package fr.mismo.demo_web_addin.service;

import java.util.List;
import java.util.Map;

public interface PieceJointeService {

    /**
     * Enregistre une pièce jointe
     * @return Les données de la pièce jointe créée
     */
    Map<String, Object> enregistrerPieceJointe(
        String nomFichier,
        Long taille,
        String typeMime,
        String idOutlook,
        String emailSource,
        String sujetMail
    );

    /**
     * Enregistre plusieurs pièces jointes
     */
    List<Map<String, Object>> enregistrerPiecesJointes(
        List<Map<String, Object>> attachments,
        String emailSource,
        String sujetMail
    );

    /**
     * Liste toutes les pièces jointes
     */
    List<Map<String, Object>> listerPiecesJointes();
}
