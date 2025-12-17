package fr.mismo.demo_web_addin.controller;

import fr.mismo.demo_web_addin.dto.ApiResponse;
import fr.mismo.demo_web_addin.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AtheneoController {

    private final EmailService emailService;
    private final InterlocuteurService interlocuteurService;
    private final DemandeService demandeService;
    private final ActionService actionService;
    private final PieceJointeService pieceJointeService;
    private final StatsService statsService;

    // ===== HEALTH CHECK =====

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        log.info("Health check appelé");

        Map<String, Object> stats = statsService.obtenirStatistiques();

        return ResponseEntity.ok(Map.of(
                "status", "ok",
                "service", "ATHENEO Demo API",
                "version", "1.0.0",
                "timestamp", LocalDateTime.now(),
                "database", "H2",
                "stats", stats
        ));
    }

    // ===== EMAILS =====

    @PostMapping("/mails")
    public ResponseEntity<ApiResponse<Map<String, Object>>> enregistrerMail(@RequestBody Map<String, Object> request) {
        try {
            // Parsing de la date ISO 8601
            String dateStr = (String) request.get("date");
            OffsetDateTime odt = OffsetDateTime.parse(dateStr);
            LocalDateTime dateReception = odt.toLocalDateTime();

            Map<String, Object> emailData = emailService.enregistrerEmail(
                    (String) request.get("from"),
                    (String) request.get("fromName"),
                    (String) request.get("subject"),
                    (String) request.get("body"),
                    dateReception
            );

            return ResponseEntity.ok(ApiResponse.success("Mail enregistré avec succès", emailData));

        } catch (Exception e) {
            log.error("❌ Erreur enregistrement mail", e);
            return ResponseEntity.badRequest().body(ApiResponse.error("Erreur: " + e.getMessage()));
        }
    }

    @GetMapping("/mails")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> listerMails(
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin,
            @RequestParam(required = false) String expediteur,
            @RequestParam(defaultValue = "100") int limite) {

        try {
            List<Map<String, Object>> emails = emailService.listerEmails(
                    dateDebut != null ? LocalDateTime.parse(dateDebut) : null,
                    dateFin != null ? LocalDateTime.parse(dateFin) : null,
                    expediteur,
                    limite
            );

            return ResponseEntity.ok(ApiResponse.success("Liste récupérée", emails));

        } catch (Exception e) {
            log.error("❌ Erreur listage emails", e);
            return ResponseEntity.badRequest().body(ApiResponse.error("Erreur: " + e.getMessage()));
        }
    }

    // ===== INTERLOCUTEURS =====

    @GetMapping("/interlocuteurs")
    public ResponseEntity<ApiResponse<Map<String, Object>>> rechercherInterlocuteur(@RequestParam String email) {
        try {
            return interlocuteurService.rechercherParEmail(email)
                    .map(interlocuteur -> ResponseEntity.ok(ApiResponse.success("Interlocuteur trouvé", interlocuteur)))
                    .orElse(ResponseEntity.ok(ApiResponse.error("Interlocuteur non trouvé")));

        } catch (Exception e) {
            log.error("❌ Erreur recherche interlocuteur", e);
            return ResponseEntity.badRequest().body(ApiResponse.error("Erreur: " + e.getMessage()));
        }
    }

    // ===== DEMANDES =====

    @PostMapping("/demandes")
    public ResponseEntity<ApiResponse<Map<String, Object>>> creerDemande(@RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");

            // Rechercher ou créer l'interlocuteur
            Long idInterlocuteur = interlocuteurService.rechercherOuCreer(email, (String) request.get("contactName"));

            Map<String, Object> demandeData = demandeService.creerDemande(
                    idInterlocuteur,
                    email,
                    (String) request.get("contactName"),
                    (String) request.get("subject"),
                    (String) request.get("description"),
                    (String) request.getOrDefault("source", "outlook_addin"),
                    (String) request.getOrDefault("priority", "normale"),
                    (String) request.getOrDefault("type", "email")
            );

            return ResponseEntity.ok(ApiResponse.success("Demande créée avec succès", demandeData));

        } catch (Exception e) {
            log.error("❌ Erreur création demande", e);
            return ResponseEntity.badRequest().body(ApiResponse.error("Erreur: " + e.getMessage()));
        }
    }

    @GetMapping("/demandes")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> listerDemandes(
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) String priorite,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long idInterlocuteur,
            @RequestParam(defaultValue = "100") int limite) {

        try {
            List<Map<String, Object>> demandes = demandeService.listerDemandes(
                    statut, priorite, type, idInterlocuteur, limite
            );

            return ResponseEntity.ok(ApiResponse.success("Liste récupérée", demandes));

        } catch (Exception e) {
            log.error("❌ Erreur listage demandes", e);
            return ResponseEntity.badRequest().body(ApiResponse.error("Erreur: " + e.getMessage()));
        }
    }

    // ===== PIÈCES JOINTES =====

    @PostMapping("/pieces-jointes")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> enregistrerPJ(@RequestBody Map<String, Object> request) {
        try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> attachments = (List<Map<String, Object>>) request.get("attachments");

            List<Map<String, Object>> savedAttachments = pieceJointeService.enregistrerPiecesJointes(
                    attachments,
                    (String) request.get("emailFrom"),
                    (String) request.get("emailSubject")
            );

            return ResponseEntity.ok(
                    ApiResponse.success(savedAttachments.size() + " pièce(s) jointe(s) enregistrée(s)", savedAttachments)
            );

        } catch (Exception e) {
            log.error("❌ Erreur enregistrement PJ", e);
            return ResponseEntity.badRequest().body(ApiResponse.error("Erreur: " + e.getMessage()));
        }
    }

    @GetMapping("/pieces-jointes")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> listerPJ() {
        try {
            List<Map<String, Object>> piecesJointes = pieceJointeService.listerPiecesJointes();
            return ResponseEntity.ok(ApiResponse.success("Liste récupérée", piecesJointes));

        } catch (Exception e) {
            log.error("❌ Erreur listage PJ", e);
            return ResponseEntity.badRequest().body(ApiResponse.error("Erreur: " + e.getMessage()));
        }
    }

    // ===== ACTIONS =====

    @PostMapping("/actions")
    public ResponseEntity<ApiResponse<Map<String, Object>>> creerAction(@RequestBody Map<String, Object> request) {
        try {
            String email = (String) request.get("email");

            // Rechercher ou créer l'interlocuteur
            Long idInterlocuteur = interlocuteurService.rechercherOuCreer(email, (String) request.get("contactName"));

            Map<String, Object> actionData = actionService.creerAction(
                    idInterlocuteur,
                    email,
                    (String) request.get("contactName"),
                    (String) request.get("title"),
                    (String) request.get("description"),
                    (String) request.getOrDefault("type", "email_follow_up"),
                    (String) request.getOrDefault("priority", "normale"),
                    (String) request.getOrDefault("status", "a_faire"),
                    (String) request.getOrDefault("source", "outlook_addin")
            );

            return ResponseEntity.ok(ApiResponse.success("Action créée avec succès", actionData));

        } catch (Exception e) {
            log.error("❌ Erreur création action", e);
            return ResponseEntity.badRequest().body(ApiResponse.error("Erreur: " + e.getMessage()));
        }
    }

    @GetMapping("/actions")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> listerActions(
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) String priorite,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long idInterlocuteur,
            @RequestParam(defaultValue = "100") int limite) {

        try {
            List<Map<String, Object>> actions = actionService.listerActions(
                    statut, priorite, type, idInterlocuteur, limite
            );

            return ResponseEntity.ok(ApiResponse.success("Liste récupérée", actions));

        } catch (Exception e) {
            log.error("❌ Erreur listage actions", e);
            return ResponseEntity.badRequest().body(ApiResponse.error("Erreur: " + e.getMessage()));
        }
    }
}
