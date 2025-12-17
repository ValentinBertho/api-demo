package fr.mismo.demo_web_addin.service;

import java.util.Map;

public interface StatsService {

    /**
     * Récupère les statistiques globales
     * @return Les statistiques (nombre d'emails, demandes, actions, etc.)
     */
    Map<String, Object> obtenirStatistiques();
}
