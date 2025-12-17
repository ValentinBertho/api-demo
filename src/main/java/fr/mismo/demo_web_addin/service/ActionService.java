package fr.mismo.demo_web_addin.service;

import java.util.List;
import java.util.Map;

public interface ActionService {

    /**
     * Crée une nouvelle action
     * @return Les données de l'action créée (id, reference, etc.)
     */
    Map<String, Object> creerAction(
        Long idInterlocuteur,
        String emailContact,
        String contactNom,
        String titre,
        String description,
        String type,
        String priorite,
        String statut,
        String source
    );

    /**
     * Liste les actions avec filtres optionnels
     */
    List<Map<String, Object>> listerActions(
        String statut,
        String priorite,
        String type,
        Long idInterlocuteur,
        int limite
    );
}
