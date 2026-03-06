package fr.mismo.demo_web_addin.projection;

import java.time.LocalDateTime;

public interface DemandeProjection {

    Long getDemandeId();          // DEMANDE_ID
    Long getNoInterlo();          // NO_INTERLO
    Long getNoSociete();          // NO_SOCIETE
    LocalDateTime getDateDemandeP(); // DATE_DEMANDE_P
    String getDemandePar();       // DEMANDE_PAR
    String getMemo();             // MEMO
    String getTitre();            // C1
    String getStatut();           // C2
    String getPriorite();         // C3
    String getType();             // C4
    String getSource();           // C5
    String getEmailContact();     // C6
    String getNomContact();       // C7
    String getReference();        // C8
}