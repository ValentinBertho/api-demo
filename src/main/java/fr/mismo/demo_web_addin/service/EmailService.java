package fr.mismo.demo_web_addin.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface EmailService {

    /**
     * Enregistre un nouvel email
     * @return L'ID de l'email créé et les données associées
     */
    Map<String, Object> enregistrerEmail(
        String expediteur,
        String expediteurNom,
        String sujet,
        String contenu,
        LocalDateTime dateReception
    );

    /**
     * Liste les emails avec filtres optionnels
     */
    List<Map<String, Object>> listerEmails(
        LocalDateTime dateDebut,
        LocalDateTime dateFin,
        String expediteur,
        int limite
    );
}
