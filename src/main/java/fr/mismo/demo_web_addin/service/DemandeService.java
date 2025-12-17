package fr.mismo.demo_web_addin.service;

import java.util.List;
import java.util.Map;

public interface DemandeService {

    /**
     * Crée une nouvelle demande
     * @return Les données de la demande créée (id, reference, etc.)
     */
    Map<String, Object> creerDemande(
        Long idInterlocuteur,
        String emailContact,
        String contactNom,
        String titre,
        String description,
        String source,
        String priorite,
        String type
    );

    /**
     * Liste les demandes avec filtres optionnels
     */
    List<Map<String, Object>> listerDemandes(
        String statut,
        String priorite,
        String type,
        Long idInterlocuteur,
        int limite
    );
}
