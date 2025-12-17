package fr.mismo.demo_web_addin.service;

import java.util.Map;
import java.util.Optional;

public interface InterlocuteurService {

    /**
     * Recherche un interlocuteur par son email
     * @return Les données de l'interlocuteur si trouvé
     */
    Optional<Map<String, Object>> rechercherParEmail(String email);

    /**
     * Crée un nouvel interlocuteur
     * @return L'ID de l'interlocuteur créé
     */
    Long creerInterlocuteur(
        String email,
        String nomComplet,
        String prenom,
        String nom,
        String societe
    );

    /**
     * Recherche ou crée un interlocuteur
     * @return L'ID de l'interlocuteur
     */
    Long rechercherOuCreer(String email, String nomComplet);
}
