package fr.mismo.demo_web_addin.controller;

import fr.mismo.demo_web_addin.dto.ApiResponse;
import fr.mismo.demo_web_addin.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Types;
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

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    // ===== HEALTH CHECK =====

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        log.info("Health check appelé");

        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("SP_ATHENEO_STATS");

            Map<String, Object> stats = jdbcCall.execute();

            return ResponseEntity.ok(Map.of(
                    "status", "ok",
                    "service", "ATHENEO Demo API",
                    "version", "1.0.0",
                    "timestamp", LocalDateTime.now(),
                    "database", "SQL Server",
                    "stats", stats
            ));
        } catch (Exception e) {
            log.error("❌ Erreur health check", e);
            return ResponseEntity.ok(Map.of(
                    "status", "ok",
                    "service", "ATHENEO Demo API",
                    "version", "1.0.0",
                    "timestamp", LocalDateTime.now(),
                    "database", "SQL Server",
                    "stats", Map.of()
            ));
        }
    }

    // ===== EMAILS =====

    @PostMapping("/mails")
    public ResponseEntity<ApiResponse<Map<String, Object>>> enregistrerMail(@RequestBody Map<String, Object> request) {
        log.info("📧 Enregistrement mail de: {}", request.get("from"));

        try {
            // Parsing de la date ISO 8601
            String dateStr = (String) request.get("date");
            OffsetDateTime odt = OffsetDateTime.parse(dateStr);
            LocalDateTime dateReception = odt.toLocalDateTime();

            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("SP_ATHENEO_ENREGISTRER_MAIL")
                    .declareParameters(
                            new SqlParameter("EXPEDITEUR", Types.VARCHAR),
                            new SqlParameter("EXPEDITEUR_NOM", Types.VARCHAR),
                            new SqlParameter("SUJET", Types.NVARCHAR),
                            new SqlParameter("CONTENU", Types.NVARCHAR),
                            new SqlParameter("DATE_RECEPTION", Types.TIMESTAMP),
                            new SqlParameter("UTILISATEUR", Types.VARCHAR),
                            new SqlOutParameter("EMAIL_ID", Types.BIGINT)
                    );

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("EXPEDITEUR", request.get("from"))
                    .addValue("EXPEDITEUR_NOM", request.get("fromName"))
                    .addValue("SUJET", request.get("subject"))
                    .addValue("CONTENU", request.get("body"))
                    .addValue("DATE_RECEPTION", dateReception)
                    .addValue("UTILISATEUR", "SYSTEM");

            Map<String, Object> result = jdbcCall.execute(params);
            Long emailId = (Long) result.get("EMAIL_ID");

            log.info("✅ Email enregistré avec ID: {}", emailId);

            Map<String, Object> emailData = Map.of(
                    "id", emailId,
                    "expediteur", request.get("from"),
                    "sujet", request.get("subject"),
                    "dateReception", dateReception
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

        log.info("📋 Listage des emails");

        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("SP_ATHENEO_LISTER_EMAILS")
                    .declareParameters(
                            new SqlParameter("DATE_DEBUT", Types.TIMESTAMP),
                            new SqlParameter("DATE_FIN", Types.TIMESTAMP),
                            new SqlParameter("EXPEDITEUR", Types.VARCHAR),
                            new SqlParameter("LIMITE", Types.INTEGER)
                    )
                    .returningResultSet("emails", (rs, rowNum) -> {
                        return Map.of(
                                "id", rs.getLong("EMAIL_ID"),
                                "expediteur", rs.getString("EXPEDITEUR"),
                                "expediteurNom", rs.getString("EXPEDITEUR_NOM"),
                                "sujet", rs.getString("SUJET"),
                                "dateReception", rs.getTimestamp("DATE_RECEPTION").toLocalDateTime()
                        );
                    });

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("DATE_DEBUT", dateDebut != null ? LocalDateTime.parse(dateDebut) : null)
                    .addValue("DATE_FIN", dateFin != null ? LocalDateTime.parse(dateFin) : null)
                    .addValue("EXPEDITEUR", expediteur)
                    .addValue("LIMITE", limite);

            Map<String, Object> result = jdbcCall.execute(params);

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> emails = (List<Map<String, Object>>) result.get("emails");

            return ResponseEntity.ok(ApiResponse.success("Liste récupérée", emails));

        } catch (Exception e) {
            log.error("❌ Erreur listage emails", e);
            return ResponseEntity.badRequest().body(ApiResponse.error("Erreur: " + e.getMessage()));
        }
    }

    // ===== INTERLOCUTEURS =====

    @GetMapping("/interlocuteurs")
    public ResponseEntity<ApiResponse<Map<String, Object>>> rechercherInterlocuteur(@RequestParam String email) {
        log.info("👤 Recherche interlocuteur: {}", email);

        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("SP_ATHENEO_RECHERCHER_INTERLOCUTEUR")
                    .declareParameters(
                            new SqlParameter("EMAIL", Types.VARCHAR)
                    )
                    .returningResultSet("interlocuteur", (rs, rowNum) -> {
                        return Map.of(
                                "id", rs.getLong("INTERLOCUTEUR_ID"),
                                "email", rs.getString("EMAIL"),
                                "prenom", rs.getString("PRENOM"),
                                "nom", rs.getString("NOM"),
                                "societe", rs.getString("SOCIETE")
                        );
                    });

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("EMAIL", email.toLowerCase());

            Map<String, Object> result = jdbcCall.execute(params);

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> interlocuteurs = (List<Map<String, Object>>) result.get("interlocuteur");

            if (interlocuteurs != null && !interlocuteurs.isEmpty()) {
                log.info("✅ Interlocuteur trouvé");
                return ResponseEntity.ok(ApiResponse.success("Interlocuteur trouvé", interlocuteurs.get(0)));
            } else {
                log.info("⚠️ Interlocuteur non trouvé");
                return ResponseEntity.ok(ApiResponse.error("Interlocuteur non trouvé"));
            }

        } catch (Exception e) {
            log.error("❌ Erreur recherche interlocuteur", e);
            return ResponseEntity.badRequest().body(ApiResponse.error("Erreur: " + e.getMessage()));
        }
    }

    private Long creerInterlocuteurAuto(String email, String nomComplet) {
        log.info("🆕 Création automatique interlocuteur: {}", email);

        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("SP_ATHENEO_CREER_INTERLOCUTEUR")
                    .declareParameters(
                            new SqlParameter("EMAIL", Types.VARCHAR),
                            new SqlParameter("NOM_COMPLET", Types.VARCHAR),
                            new SqlParameter("PRENOM", Types.VARCHAR),
                            new SqlParameter("NOM", Types.VARCHAR),
                            new SqlParameter("SOCIETE", Types.VARCHAR),
                            new SqlParameter("UTILISATEUR", Types.VARCHAR),
                            new SqlOutParameter("INTERLOCUTEUR_ID", Types.BIGINT)
                    );

            String prenom = null;
            String nom = null;

            if (nomComplet != null && nomComplet.contains(" ")) {
                String[] parts = nomComplet.split(" ", 2);
                prenom = parts[0];
                nom = parts[1];
            } else {
                nom = nomComplet != null ? nomComplet : email.split("@")[0];
            }

            String domaine = email.split("@")[1];
            String societe = domaine.split("\\.")[0].toUpperCase();

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("EMAIL", email.toLowerCase())
                    .addValue("NOM_COMPLET", nomComplet)
                    .addValue("PRENOM", prenom)
                    .addValue("NOM", nom)
                    .addValue("SOCIETE", societe)
                    .addValue("UTILISATEUR", "SYSTEM");

            Map<String, Object> result = jdbcCall.execute(params);
            Long interlocuteurId = (Long) result.get("INTERLOCUTEUR_ID");

            log.info("✅ Interlocuteur créé avec ID: {}", interlocuteurId);
            return interlocuteurId;

        } catch (Exception e) {
            log.error("❌ Erreur création interlocuteur", e);
            throw new RuntimeException("Impossible de créer l'interlocuteur", e);
        }
    }

    // ===== DEMANDES =====

    @PostMapping("/demandes")
    public ResponseEntity<ApiResponse<Map<String, Object>>> creerDemande(@RequestBody Map<String, Object> request) {
        String email = (String) request.get("email");
        log.info("📋 Création demande pour: {}", email);

        try {
            // Rechercher ou créer l'interlocuteur
            Long idInterlocuteur = rechercherOuCreerInterlocuteur(email, (String) request.get("contactName"));

            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("SP_ATHENEO_CREER_DEMANDE")
                    .declareParameters(
                            new SqlParameter("ID_INTERLOCUTEUR", Types.BIGINT),
                            new SqlParameter("EMAIL_CONTACT", Types.VARCHAR),
                            new SqlParameter("CONTACT_NOM", Types.VARCHAR),
                            new SqlParameter("TITRE", Types.NVARCHAR),
                            new SqlParameter("DESCRIPTION", Types.NVARCHAR),
                            new SqlParameter("SOURCE", Types.VARCHAR),
                            new SqlParameter("PRIORITE", Types.VARCHAR),
                            new SqlParameter("TYPE", Types.VARCHAR),
                            new SqlParameter("UTILISATEUR", Types.VARCHAR),
                            new SqlOutParameter("DEMANDE_ID", Types.BIGINT),
                            new SqlOutParameter("REFERENCE", Types.VARCHAR)
                    );

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("ID_INTERLOCUTEUR", idInterlocuteur)
                    .addValue("EMAIL_CONTACT", email)
                    .addValue("CONTACT_NOM", request.get("contactName"))
                    .addValue("TITRE", request.get("subject"))
                    .addValue("DESCRIPTION", request.get("description"))
                    .addValue("SOURCE", request.getOrDefault("source", "outlook_addin"))
                    .addValue("PRIORITE", request.getOrDefault("priority", "normale"))
                    .addValue("TYPE", request.getOrDefault("type", "email"))
                    .addValue("UTILISATEUR", "SYSTEM");

            Map<String, Object> result = jdbcCall.execute(params);

            Long demandeId = (Long) result.get("DEMANDE_ID");
            String reference = (String) result.get("REFERENCE");

            log.info("✅ Demande créée: {}", reference);

            Map<String, Object> demandeData = Map.of(
                    "id", demandeId,
                    "reference", reference,
                    "idInterlocuteur", idInterlocuteur,
                    "titre", request.get("subject")
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

        log.info("📋 Listage des demandes");

        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("SP_ATHENEO_LISTER_DEMANDES")
                    .declareParameters(
                            new SqlParameter("STATUT", Types.VARCHAR),
                            new SqlParameter("PRIORITE", Types.VARCHAR),
                            new SqlParameter("TYPE", Types.VARCHAR),
                            new SqlParameter("ID_INTERLOCUTEUR", Types.BIGINT),
                            new SqlParameter("LIMITE", Types.INTEGER)
                    )
                    .returningResultSet("demandes", (rs, rowNum) -> {
                        return Map.of(
                                "id", rs.getLong("DEMANDE_ID"),
                                "reference", rs.getString("REFERENCE"),
                                "idInterlocuteur", rs.getLong("ID_INTERLOCUTEUR"),
                                "titre", rs.getString("TITRE"),
                                "priorite", rs.getString("PRIORITE"),
                                "statut", rs.getString("STATUT")
                        );
                    });

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("STATUT", statut)
                    .addValue("PRIORITE", priorite)
                    .addValue("TYPE", type)
                    .addValue("ID_INTERLOCUTEUR", idInterlocuteur)
                    .addValue("LIMITE", limite);

            Map<String, Object> result = jdbcCall.execute(params);

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> demandes = (List<Map<String, Object>>) result.get("demandes");

            return ResponseEntity.ok(ApiResponse.success("Liste récupérée", demandes));

        } catch (Exception e) {
            log.error("❌ Erreur listage demandes", e);
            return ResponseEntity.badRequest().body(ApiResponse.error("Erreur: " + e.getMessage()));
        }
    }

    // ===== PIÈCES JOINTES =====

    @PostMapping("/pieces-jointes")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> enregistrerPJ(@RequestBody Map<String, Object> request) {
        log.info("📎 Enregistrement pièces jointes");

        try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> attachments = (List<Map<String, Object>>) request.get("attachments");

            List<Map<String, Object>> savedAttachments = attachments.stream().map(att -> {
                try {
                    SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                            .withProcedureName("SP_ATHENEO_ENREGISTRER_PIECE_JOINTE")
                            .declareParameters(
                                    new SqlParameter("NOM_FICHIER", Types.VARCHAR),
                                    new SqlParameter("TAILLE", Types.BIGINT),
                                    new SqlParameter("TYPE_MIME", Types.VARCHAR),
                                    new SqlParameter("ID_OUTLOOK", Types.VARCHAR),
                                    new SqlParameter("EMAIL_SOURCE", Types.VARCHAR),
                                    new SqlParameter("SUJET_MAIL", Types.NVARCHAR),
                                    new SqlParameter("UTILISATEUR", Types.VARCHAR),
                                    new SqlOutParameter("PIECE_JOINTE_ID", Types.BIGINT)
                            );

                    MapSqlParameterSource params = new MapSqlParameterSource()
                            .addValue("NOM_FICHIER", att.get("name"))
                            .addValue("TAILLE", ((Number) att.get("size")).longValue())
                            .addValue("TYPE_MIME", att.get("contentType"))
                            .addValue("ID_OUTLOOK", att.get("id"))
                            .addValue("EMAIL_SOURCE", request.get("emailFrom"))
                            .addValue("SUJET_MAIL", request.get("emailSubject"))
                            .addValue("UTILISATEUR", "SYSTEM");

                    Map<String, Object> result = jdbcCall.execute(params);
                    Long pieceJointeId = (Long) result.get("PIECE_JOINTE_ID");

                    return Map.of(
                            "id", pieceJointeId,
                            "nomFichier", att.get("name"),
                            "taille", att.get("size")
                    );

                } catch (Exception e) {
                    log.error("❌ Erreur enregistrement PJ", e);
                    throw new RuntimeException("Erreur enregistrement PJ", e);
                }
            }).toList();

            log.info("✅ {} PJ enregistrées", savedAttachments.size());
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
        log.info("📎 Listage des pièces jointes");

        try {
            String sql = "SELECT * FROM PIECE_JOINTE ORDER BY DATE_CREATION DESC";
            List<Map<String, Object>> piecesJointes = jdbcTemplate.queryForList(sql);

            return ResponseEntity.ok(ApiResponse.success("Liste récupérée", piecesJointes));

        } catch (Exception e) {
            log.error("❌ Erreur listage PJ", e);
            return ResponseEntity.badRequest().body(ApiResponse.error("Erreur: " + e.getMessage()));
        }
    }

    // ===== ACTIONS =====

    @PostMapping("/actions")
    public ResponseEntity<ApiResponse<Map<String, Object>>> creerAction(@RequestBody Map<String, Object> request) {
        String email = (String) request.get("email");
        log.info("✅ Création action pour: {}", email);

        try {
            // Rechercher ou créer l'interlocuteur
            Long idInterlocuteur = rechercherOuCreerInterlocuteur(email, (String) request.get("contactName"));

            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("SP_ATHENEO_CREER_ACTION")
                    .declareParameters(
                            new SqlParameter("ID_INTERLOCUTEUR", Types.BIGINT),
                            new SqlParameter("EMAIL_CONTACT", Types.VARCHAR),
                            new SqlParameter("CONTACT_NOM", Types.VARCHAR),
                            new SqlParameter("TITRE", Types.NVARCHAR),
                            new SqlParameter("DESCRIPTION", Types.NVARCHAR),
                            new SqlParameter("TYPE", Types.VARCHAR),
                            new SqlParameter("PRIORITE", Types.VARCHAR),
                            new SqlParameter("STATUT", Types.VARCHAR),
                            new SqlParameter("SOURCE", Types.VARCHAR),
                            new SqlParameter("UTILISATEUR", Types.VARCHAR),
                            new SqlOutParameter("ACTION_ID", Types.BIGINT),
                            new SqlOutParameter("REFERENCE", Types.VARCHAR)
                    );

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("ID_INTERLOCUTEUR", idInterlocuteur)
                    .addValue("EMAIL_CONTACT", email)
                    .addValue("CONTACT_NOM", request.get("contactName"))
                    .addValue("TITRE", request.get("title"))
                    .addValue("DESCRIPTION", request.get("description"))
                    .addValue("TYPE", request.getOrDefault("type", "email_follow_up"))
                    .addValue("PRIORITE", request.getOrDefault("priority", "normale"))
                    .addValue("STATUT", request.getOrDefault("status", "a_faire"))
                    .addValue("SOURCE", request.getOrDefault("source", "outlook_addin"))
                    .addValue("UTILISATEUR", "SYSTEM");

            Map<String, Object> result = jdbcCall.execute(params);

            Long actionId = (Long) result.get("ACTION_ID");
            String reference = (String) result.get("REFERENCE");

            log.info("✅ Action créée: {}", reference);

            Map<String, Object> actionData = Map.of(
                    "id", actionId,
                    "reference", reference,
                    "idInterlocuteur", idInterlocuteur,
                    "titre", request.get("title")
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

        log.info("✅ Listage des actions");

        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("SP_ATHENEO_LISTER_ACTIONS")
                    .declareParameters(
                            new SqlParameter("STATUT", Types.VARCHAR),
                            new SqlParameter("PRIORITE", Types.VARCHAR),
                            new SqlParameter("TYPE", Types.VARCHAR),
                            new SqlParameter("ID_INTERLOCUTEUR", Types.BIGINT),
                            new SqlParameter("LIMITE", Types.INTEGER)
                    )
                    .returningResultSet("actions", (rs, rowNum) -> {
                        return Map.of(
                                "id", rs.getLong("ACTION_ID"),
                                "reference", rs.getString("REFERENCE"),
                                "idInterlocuteur", rs.getLong("ID_INTERLOCUTEUR"),
                                "titre", rs.getString("TITRE"),
                                "priorite", rs.getString("PRIORITE"),
                                "statut", rs.getString("STATUT")
                        );
                    });

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("STATUT", statut)
                    .addValue("PRIORITE", priorite)
                    .addValue("TYPE", type)
                    .addValue("ID_INTERLOCUTEUR", idInterlocuteur)
                    .addValue("LIMITE", limite);

            Map<String, Object> result = jdbcCall.execute(params);

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> actions = (List<Map<String, Object>>) result.get("actions");

            return ResponseEntity.ok(ApiResponse.success("Liste récupérée", actions));

        } catch (Exception e) {
            log.error("❌ Erreur listage actions", e);
            return ResponseEntity.badRequest().body(ApiResponse.error("Erreur: " + e.getMessage()));
        }
    }

    // ===== MÉTHODE UTILITAIRE =====

    private Long rechercherOuCreerInterlocuteur(String email, String nomComplet) {
        try {
            // Rechercher d'abord
            SimpleJdbcCall searchCall = new SimpleJdbcCall(dataSource)
                    .withProcedureName("SP_ATHENEO_RECHERCHER_INTERLOCUTEUR")
                    .declareParameters(new SqlParameter("EMAIL", Types.VARCHAR))
                    .returningResultSet("interlocuteur", (rs, rowNum) -> rs.getLong("INTERLOCUTEUR_ID"));

            MapSqlParameterSource searchParams = new MapSqlParameterSource()
                    .addValue("EMAIL", email.toLowerCase());

            Map<String, Object> searchResult = searchCall.execute(searchParams);

            @SuppressWarnings("unchecked")
            List<Long> interlocuteurs = (List<Long>) searchResult.get("interlocuteur");

            if (interlocuteurs != null && !interlocuteurs.isEmpty()) {
                return interlocuteurs.get(0);
            }

            // Sinon créer
            return creerInterlocuteurAuto(email, nomComplet);

        } catch (Exception e) {
            log.error("❌ Erreur recherche/création interlocuteur", e);
            throw new RuntimeException("Impossible de gérer l'interlocuteur", e);
        }
    }
}