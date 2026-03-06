package fr.mismo.demo_web_addin.controller;

import fr.mismo.demo_web_addin.dto.ApiResponse;
import fr.mismo.demo_web_addin.dto.CreateDemandeRequest;
import fr.mismo.demo_web_addin.dto.PieceJointeRequest;
import fr.mismo.demo_web_addin.projection.InterlocuteurProjection;
import fr.mismo.demo_web_addin.properties.WsDocumentProperties;
import fr.mismo.demo_web_addin.services.*;
import fr.mismo.demo_web_addin.util.ContexteParser;
import fr.mismo.demo_web_addin.util.FilesUtil;
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
    private final fr.mismo.demo_web_addin.service.DemandeService demandeService;
    private final PieceJointeService pieceJointeService;
    private final WsDocumentService documentService;
    private final FilesUtil util;
    private final WsDocumentProperties wsDocumentProperties;

    // =========================
    // HEALTH
    // =========================

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

    /**
     * Enregistre le fichier mail (.eml) dans ATHENEO sur le contexte courant
     * de l'utilisateur destinataire (param "to").
     *
     * Flux :
     *  1. Récupère le module actif de l'utilisateur "to" via T_SESSION
     *  2. Parse le contexte → clé/valeur ATHENEO (ex: NO_DEVIS=69392)
     *  3. Crée un fichier temporaire et appelle wsDocumentService.creerDocument
     */
    @PostMapping(value = "/mails", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> enregistrerMail(
            @RequestParam("file") MultipartFile file,
            @RequestParam String to,
            @RequestParam String from,
            @RequestParam String fromName,
            @RequestParam String subject,
            @RequestParam String body,
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
    /**
     * Recherche un interlocuteur par email (expéditeur "from").
     * Le front utilise ensuite l'id retourné pour construire : ath:Consulter?INTERLOC/{id}
     */
    @GetMapping("/interlocuteurs")
    public ResponseEntity<ApiResponse<InterlocuteurProjection>> rechercherInterlocuteur(
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
    /**
     * Crée une demande ATHENEO depuis les données de l'email.
     * Retourne l'ID de la demande créée.
     * Le front construit ensuite : ath:Consulter?DEMANDE/{id}
     */
    @PostMapping("/demandes")
    public ResponseEntity<ApiResponse<?>> creerDemande(
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
    /**
     * Enregistre une ou plusieurs pièces jointes dans ATHENEO
     * sur le contexte courant de l'utilisateur destinataire.
     *
     * Flux identique à /mails :
     *  1. Récupère le module actif du destinataire
     *  2. Parse le contexte → clé/valeur ATHENEO
     *  3. Pour chaque fichier : crée un temp, appelle wsDocumentService.creerDocument
     */
    @PostMapping(value = "/pieces-jointes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<?>> enregistrerPiecesJointes(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam String to,
            @RequestParam String from,
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