package fr.mismo.demo_web_addin.controller;

import fr.mismo.demo_web_addin.dto.ApiResponse;
import fr.mismo.demo_web_addin.model.*;
import fr.mismo.demo_web_addin.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AtheneoController {

    private final EmailRepository emailRepository;
    private final InterlocuteurRepository interlocuteurRepository;
    private final DemandeRepository demandeRepository;
    private final PieceJointeRepository pieceJointeRepository;
    private final ActionRepository actionRepository;

    private long demandeCounter = 1;
    private long actionCounter = 1;

    // ===== HEALTH CHECK =====

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        log.info("Health check appelé");
        return ResponseEntity.ok(Map.of(
                "status", "ok",
                "service", "ATHENEO Demo API",
                "version", "1.0.0",
                "timestamp", LocalDateTime.now(),
                "database", "H2 (en mémoire)",
                "stats", Map.of(
                        "emails", emailRepository.count(),
                        "demandes", demandeRepository.count(),
                        "contacts", interlocuteurRepository.count(),
                        "actions", actionRepository.count()
                )
        ));
    }

    // ===== EMAILS =====

    @PostMapping("/mails")
    public ResponseEntity<ApiResponse<Email>> enregistrerMail(@RequestBody Map<String, Object> request) {
        log.info("📧 Enregistrement mail de: {}", request.get("from"));

        try {
            Email email = new Email();
            email.setExpediteur((String) request.get("from"));
            email.setExpediteurNom((String) request.get("fromName"));
            email.setSujet((String) request.get("subject"));
            email.setContenu((String) request.get("body"));
            email.setDateReception(LocalDateTime.parse((String) request.get("date")));

            Email saved = emailRepository.save(email);

            log.info("✅ Email enregistré avec ID: {}", saved.getId());
            return ResponseEntity.ok(
                    ApiResponse.success("Mail enregistré avec succès", saved)
            );
        } catch (Exception e) {
            log.error("❌ Erreur enregistrement mail", e);
            return ResponseEntity.badRequest().body(
                    ApiResponse.error("Erreur: " + e.getMessage())
            );
        }
    }

    @GetMapping("/mails")
    public ResponseEntity<List<Email>> listerMails() {
        return ResponseEntity.ok(emailRepository.findAll());
    }

    // ===== INTERLOCUTEURS =====

    @GetMapping("/interlocuteurs")
    public ResponseEntity<ApiResponse<Interlocuteur>> rechercherInterlocuteur(@RequestParam String email) {
        log.info("👤 Recherche interlocuteur: {}", email);

        var interlocuteur = interlocuteurRepository.findByEmail(email.toLowerCase());

        if (interlocuteur.isPresent()) {
            log.info("✅ Interlocuteur trouvé: {}", interlocuteur.get().getNom());
            return ResponseEntity.ok(
                    ApiResponse.success("Interlocuteur trouvé", interlocuteur.get())
            );
        } else {
            log.info("⚠️ Interlocuteur non trouvé");
            return ResponseEntity.ok(
                    ApiResponse.error("Interlocuteur non trouvé")
            );
        }
    }

    private Interlocuteur creerInterlocuteurAuto(String email, String nom) {
        Interlocuteur interlocuteur = new Interlocuteur();
        interlocuteur.setEmail(email.toLowerCase());

        if (nom != null && nom.contains(" ")) {
            String[] parts = nom.split(" ", 2);
            interlocuteur.setPrenom(parts[0]);
            interlocuteur.setNom(parts[1]);
        } else {
            interlocuteur.setNom(nom != null ? nom : email.split("@")[0]);
        }

        String domaine = email.split("@")[1];
        interlocuteur.setSociete(domaine.split("\\.")[0].toUpperCase());

        return interlocuteurRepository.save(interlocuteur);
    }

    // ===== DEMANDES =====

    @PostMapping("/demandes")
    public ResponseEntity<ApiResponse<Demande>> creerDemande(@RequestBody Map<String, Object> request) {
        String email = (String) request.get("email");
        log.info("📋 Création demande pour: {}", email);

        try {
            // Créer ou trouver l'interlocuteur
            var interlocuteur = interlocuteurRepository.findByEmail(email.toLowerCase())
                    .orElseGet(() -> creerInterlocuteurAuto(email, (String) request.get("contactName")));

            Demande demande = new Demande();
            demande.setReference("DEM-2024-" + String.format("%05d", demandeCounter++));
            demande.setIdInterlocuteur(interlocuteur.getId());
            demande.setEmailContact(email);
            demande.setContactNom((String) request.get("contactName"));
            demande.setTitre((String) request.get("subject"));
            demande.setDescription((String) request.get("description"));
            demande.setSource((String) request.getOrDefault("source", "outlook_addin"));
            demande.setPriorite((String) request.getOrDefault("priority", "normale"));
            demande.setType((String) request.getOrDefault("type", "email"));

            Demande saved = demandeRepository.save(demande);

            log.info("✅ Demande créée: {}", saved.getReference());
            return ResponseEntity.ok(
                    ApiResponse.success("Demande créée avec succès", saved)
            );
        } catch (Exception e) {
            log.error("❌ Erreur création demande", e);
            return ResponseEntity.badRequest().body(
                    ApiResponse.error("Erreur: " + e.getMessage())
            );
        }
    }

    @GetMapping("/demandes")
    public ResponseEntity<List<Demande>> listerDemandes() {
        return ResponseEntity.ok(demandeRepository.findAll());
    }

    // ===== PIÈCES JOINTES =====

    @PostMapping("/pieces-jointes")
    public ResponseEntity<ApiResponse<List<PieceJointe>>> enregistrerPJ(@RequestBody Map<String, Object> request) {
        log.info("📎 Enregistrement pièces jointes");

        try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> attachments = (List<Map<String, Object>>) request.get("attachments");
            List<PieceJointe> saved = attachments.stream().map(att -> {
                PieceJointe pj = new PieceJointe();
                pj.setNomFichier((String) att.get("name"));
                pj.setTaille(((Number) att.get("size")).longValue());
                pj.setTypeMime((String) att.get("contentType"));
                pj.setIdOutlook((String) att.get("id"));
                pj.setEmailSource((String) request.get("emailFrom"));
                pj.setSujetMail((String) request.get("emailSubject"));
                return pieceJointeRepository.save(pj);
            }).toList();

            log.info("✅ {} PJ enregistrées", saved.size());
            return ResponseEntity.ok(
                    ApiResponse.success(saved.size() + " pièce(s) jointe(s) enregistrée(s)", saved)
            );
        } catch (Exception e) {
            log.error("❌ Erreur enregistrement PJ", e);
            return ResponseEntity.badRequest().body(
                    ApiResponse.error("Erreur: " + e.getMessage())
            );
        }
    }

    @GetMapping("/pieces-jointes")
    public ResponseEntity<List<PieceJointe>> listerPJ() {
        return ResponseEntity.ok(pieceJointeRepository.findAll());
    }

    // ===== ACTIONS =====

    @PostMapping("/actions")
    public ResponseEntity<ApiResponse<Action>> creerAction(@RequestBody Map<String, Object> request) {
        String email = (String) request.get("email");
        log.info("✅ Création action pour: {}", email);

        try {
            var interlocuteur = interlocuteurRepository.findByEmail(email.toLowerCase())
                    .orElseGet(() -> creerInterlocuteurAuto(email, (String) request.get("contactName")));

            Action action = new Action();
            action.setReference("ACT-2024-" + String.format("%05d", actionCounter++));
            action.setIdInterlocuteur(interlocuteur.getId());
            action.setEmailContact(email);
            action.setContactNom((String) request.get("contactName"));
            action.setTitre((String) request.get("title"));
            action.setDescription((String) request.get("description"));
            action.setType((String) request.getOrDefault("type", "email_follow_up"));
            action.setPriorite((String) request.getOrDefault("priority", "normale"));
            action.setStatut((String) request.getOrDefault("status", "a_faire"));
            action.setSource((String) request.getOrDefault("source", "outlook_addin"));

            Action saved = actionRepository.save(action);

            log.info("✅ Action créée: {}", saved.getReference());
            return ResponseEntity.ok(
                    ApiResponse.success("Action créée avec succès", saved)
            );
        } catch (Exception e) {
            log.error("❌ Erreur création action", e);
            return ResponseEntity.badRequest().body(
                    ApiResponse.error("Erreur: " + e.getMessage())
            );
        }
    }

    @GetMapping("/actions")
    public ResponseEntity<List<Action>> listerActions() {
        return ResponseEntity.ok(actionRepository.findAll());
    }
}