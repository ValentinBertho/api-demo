package fr.mismo.demo_web_addin.controller;

import fr.mismo.demo_web_addin.dto.ApiResponse;
import fr.mismo.demo_web_addin.dto.CreateDemandeRequest;
import fr.mismo.demo_web_addin.dto.PieceJointeRequest;
import fr.mismo.demo_web_addin.projection.InterlocuteurProjection;
import fr.mismo.demo_web_addin.properties.WsDocumentProperties;
import fr.mismo.demo_web_addin.services.*;
import fr.mismo.demo_web_addin.util.ContexteParser;
import fr.mismo.demo_web_addin.util.FilesUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AtheneoController {

    private final MailService mailService;
    private final InterlocuteurService interlocuteurService;
    private final fr.mismo.demo_web_addin.services.DemandeService demandeService;
    private final PieceJointeService pieceJointeService;
    private final WsDocumentService documentService;
    private final FilesUtil util;
    private final WsDocumentProperties wsDocumentProperties;

    // =========================
    // HEALTH
    // =========================

    @Tag(name = "Health")
    @Operation(
            summary = "Vérification de l'état du service",
            description = "Retourne le statut du service ainsi que la date et heure courante."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Service opérationnel",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = @ExampleObject(value = """
                            {
                              "status": "ok",
                              "service": "ATHENEO Demo API",
                              "timestamp": "2024-01-15T10:30:00"
                            }
                            """)
            )
    )
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "ok",
                "service", "ATHENEO Demo API",
                "timestamp", LocalDateTime.now()
        ));
    }

    // =========================
    // MAIL — Enregistrer
    // =========================

    @Tag(name = "Mails")
    @Operation(
            summary = "Enregistrer un e-mail dans ATHENEO",
            description = """
                    Enregistre le fichier mail (.eml) dans ATHENEO sur le contexte courant
                    de l'utilisateur destinataire (paramètre `to`).

                    **Flux interne :**
                    1. Récupère le module actif de l'utilisateur `to` via `T_SESSION`
                    2. Parse le contexte → clé/valeur ATHENEO (ex : `NO_DEVIS=69392`)
                    3. Téléverse le fichier .eml via le service SOAP `WSDocument`
                    4. Enregistre les métadonnées de l'e-mail en base via la procédure stockée `SP_ATHENEO_ENREGISTRER_MAIL`
                    """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "E-mail enregistré avec succès",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                                    {
                                      "success": true,
                                      "message": "Mail enregistré",
                                      "data": {
                                        "email_id": 1042,
                                        "expediteur": "client@example.com",
                                        "sujet": "Demande d'intervention urgente"
                                      }
                                    }
                                    """)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Erreur lors du téléversement du fichier",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                                    {
                                      "success": false,
                                      "message": "Erreur lors du téléversement du fichier",
                                      "data": null
                                    }
                                    """)
                    )
            )
    })
    @PostMapping(value = "/mails", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> enregistrerMail(
            @Parameter(description = "Fichier .eml de l'e-mail", required = true)
            @RequestParam("file") MultipartFile file,

            @Parameter(description = "Adresse e-mail du destinataire (utilisateur ATHENEO connecté)", required = true, example = "conseiller@atheneo.fr")
            @RequestParam String to,

            @Parameter(description = "Adresse e-mail de l'expéditeur", required = true, example = "client@example.com")
            @RequestParam String from,

            @Parameter(description = "Nom affiché de l'expéditeur", required = true, example = "Jean Dupont")
            @RequestParam String fromName,

            @Parameter(description = "Objet de l'e-mail", required = true, example = "Demande d'intervention urgente")
            @RequestParam String subject,

            @Parameter(description = "Corps HTML de l'e-mail", required = true)
            @RequestParam String body,

            @Parameter(description = "Date de réception au format ISO-8601", required = true, example = "2024-01-15T10:30:00")
            @RequestParam String date
    ) {
        log.info("📧 Enregistrement mail — from={} to={}", from, to);

        // 1. Contexte de l'utilisateur destinataire → "Fiche Devis - N°69392"
        final String module = interlocuteurService.rechercherContexte(to).getModule();
        log.info("Contexte récupéré : {}", module);

        // 2. Parse → {NO_DEVIS : "69392"}
        HashMap<String, String> listeCleValeur = new HashMap<>();
        ContexteParser.parse(module).ifPresentOrElse(
                entry -> {
                    listeCleValeur.put(entry.getKey(), entry.getValue());
                    log.info("Contexte parsé : {} = {}", entry.getKey(), entry.getValue());
                },
                () -> log.warn("⚠️ Contexte non reconnu ou non parsable : {}", module)
        );

        // 3. Téléversement
        try {
            final String ext      = util.getFileExtension(file.getOriginalFilename());
            final String nomSans  = util.removeExtension(file.getOriginalFilename());
            final Path   tempFile = Files.createTempFile("temp_MAIL_", "." + ext);
            Files.write(tempFile, file.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

            documentService.creerDocument(
                    wsDocumentProperties.getProprietesMail(),
                    nomSans,
                    ext,
                    tempFile,
                    listeCleValeur,
                    from
            );

            deleteTempFile(tempFile);

        } catch (IOException e) {
            log.error("Erreur téléversement mail : {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Erreur lors du téléversement du fichier"));
        }

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Mail enregistré",
                        mailService.enregistrerMail(file, from, fromName, subject, body, date)
                )
        );
    }

    // =========================
    // INTERLOCUTEURS
    // =========================

    @Tag(name = "Interlocuteurs")
    @Operation(
            summary = "Rechercher un interlocuteur par e-mail",
            description = """
                    Recherche un interlocuteur ATHENEO à partir de son adresse e-mail (expéditeur).

                    En cas de succès, le front-end utilise l'identifiant retourné pour construire
                    le lien de consultation : `ath:Consulter?INTERLOC/{id}`
                    """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Interlocuteur trouvé ou non trouvé (voir le champ `success`)",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(name = "Trouvé", value = """
                                            {
                                              "success": true,
                                              "message": "Interlocuteur trouvé",
                                              "data": {
                                                "id": 312,
                                                "nom": "Dupont",
                                                "prenom": "Jean",
                                                "email": "client@example.com",
                                                "telephone": "0123456789",
                                                "telephonePortable": "0612345678",
                                                "societe": "Example Corp",
                                                "noSociete": 47,
                                                "actif": true
                                              }
                                            }
                                            """),
                                    @ExampleObject(name = "Non trouvé", value = """
                                            {
                                              "success": false,
                                              "message": "Interlocuteur non trouvé",
                                              "data": null
                                            }
                                            """)
                            }
                    )
            )
    })
    @GetMapping("/interlocuteurs")
    public ResponseEntity<ApiResponse<InterlocuteurProjection>> rechercherInterlocuteur(
            @Parameter(description = "Adresse e-mail de l'expéditeur à rechercher", required = true, example = "client@example.com")
            @RequestParam String email
    ) {
        log.info("🔍 Recherche interlocuteur — email={}", email);

        InterlocuteurProjection interlocuteur = interlocuteurService.rechercher(email);

        if (interlocuteur == null) {
            return ResponseEntity.ok(ApiResponse.error("Interlocuteur non trouvé"));
        }

        return ResponseEntity.ok(ApiResponse.success("Interlocuteur trouvé", interlocuteur));
    }

    // =========================
    // DEMANDES
    // =========================

    @Tag(name = "Demandes")
    @Operation(
            summary = "Créer une demande (incident) depuis un e-mail",
            description = """
                    Crée une demande ATHENEO à partir des données d'un e-mail reçu.

                    La procédure stockée `SP_ATHENEO_CREER_DEMANDE` est appelée en interne.
                    Le front-end utilise ensuite l'identifiant retourné pour construire
                    le lien de consultation : `ath:Consulter?DEMANDE/{id}`
                    """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Demande créée avec succès",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                                    {
                                      "success": true,
                                      "message": "Demande créée",
                                      "data": {
                                        "demande_id": 8851,
                                        "reference": "INC-2024-08851"
                                      }
                                    }
                                    """)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Erreur serveur lors de la création"
            )
    })
    @PostMapping("/demandes")
    public ResponseEntity<ApiResponse<?>> creerDemande(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Données de la demande à créer",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateDemandeRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "email": "client@example.com",
                                      "contactName": "Jean Dupont",
                                      "subject": "Panne sur le module de facturation",
                                      "description": "Depuis ce matin, impossible d'accéder à la liste des factures.",
                                      "source": "EMAIL",
                                      "priority": "HAUTE",
                                      "type": "INCIDENT"
                                    }
                                    """)
                    )
            )
            @RequestBody CreateDemandeRequest request
    ) {
        log.info("📋 Création demande — from={}", request.getEmail());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Demande créée",
                        demandeService.creerDemande(request)
                )
        );
    }

    // =========================
    // PIÈCES JOINTES
    // =========================

    @Tag(name = "Pièces jointes")
    @Operation(
            summary = "Enregistrer des pièces jointes dans ATHENEO",
            description = """
                    Enregistre une ou plusieurs pièces jointes dans ATHENEO sur le contexte courant
                    de l'utilisateur destinataire.

                    **Flux interne (identique à `/mails`) :**
                    1. Récupère le module actif du destinataire `to` via `T_SESSION`
                    2. Parse le contexte → clé/valeur ATHENEO
                    3. Pour chaque fichier : crée un fichier temporaire et l'envoie via `WSDocument`
                    4. Enregistre les métadonnées via `SP_ATHENEO_ENREGISTRER_PIECE_JOINTE`

                    Retourne le nombre de pièces jointes enregistrées avec succès.
                    """
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Pièces jointes enregistrées",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                                    {
                                      "success": true,
                                      "message": "2 pièce(s) jointe(s) enregistrée(s)",
                                      "data": { "count": 2 }
                                    }
                                    """)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Aucune pièce jointe n'a pu être enregistrée",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(value = """
                                    {
                                      "success": false,
                                      "message": "Aucune pièce jointe n'a pu être enregistrée",
                                      "data": null
                                    }
                                    """)
                    )
            )
    })
    @PostMapping(value = "/pieces-jointes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> enregistrerPiecesJointes(
            @Parameter(description = "Liste des fichiers à enregistrer (multipart)", required = true)
            @RequestParam("files") List<MultipartFile> files,

            @Parameter(description = "Adresse e-mail du destinataire (utilisateur ATHENEO connecté)", required = true, example = "conseiller@atheneo.fr")
            @RequestParam String to,

            @Parameter(description = "Adresse e-mail de l'expéditeur", required = true, example = "client@example.com")
            @RequestParam String from,

            @Parameter(description = "Objet de l'e-mail d'origine", required = true, example = "Devis n°69392 - documents joints")
            @RequestParam String subject
    ) {
        log.info("📎 Enregistrement PJ — {} fichier(s) — from={} to={}", files.size(), from, to);

        // 1. Contexte du destinataire
        final String module = interlocuteurService.rechercherContexte(to).getModule();
        log.info("Contexte PJ : {}", module);

        // 2. Parse contexte
        HashMap<String, String> listeCleValeur = new HashMap<>();
        ContexteParser.parse(module).ifPresentOrElse(
                entry -> {
                    listeCleValeur.put(entry.getKey(), entry.getValue());
                    log.info("Contexte parsé : {} = {}", entry.getKey(), entry.getValue());
                },
                () -> log.warn("⚠️ Contexte non reconnu pour PJ : {}", module)
        );

        int nbOk = 0;

        // 3. Téléversement fichier par fichier
        for (MultipartFile file : files) {
            try {
                final String ext      = util.getFileExtension(file.getOriginalFilename());
                final String nomSans  = util.removeExtension(file.getOriginalFilename());
                final Path   tempFile = Files.createTempFile("temp_PJ_", "." + ext);
                Files.write(tempFile, file.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

                documentService.creerDocument(
                        wsDocumentProperties.getProprietesPieceJointe(),
                        nomSans,
                        ext,
                        tempFile,
                        listeCleValeur,
                        from
                );

                deleteTempFile(tempFile);
                nbOk++;

            } catch (IOException e) {
                log.error("Erreur PJ {} : {}", file.getOriginalFilename(), e.getMessage(), e);
            }
        }

        if (nbOk == 0) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Aucune pièce jointe n'a pu être enregistrée"));
        }

        return ResponseEntity.ok(
                ApiResponse.success(
                        nbOk + " pièce(s) jointe(s) enregistrée(s)",
                        Map.of("count", nbOk)
                )
        );
    }

    // =========================
    // PRIVATE HELPERS
    // =========================

    private void deleteTempFile(Path tempFile) {
        try {
            Files.delete(tempFile);
        } catch (IOException e) {
            log.error("Erreur suppression fichier temporaire : {}", e.getMessage(), e);
        }
    }
}
