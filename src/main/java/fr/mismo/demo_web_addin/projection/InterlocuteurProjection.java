package fr.mismo.demo_web_addin.projection;

import java.time.LocalDateTime;

public interface InterlocuteurProjection {
    Long getId();
    String getNom();
    String getPrenom();
    String getEmail();
    String getTelephone();
    String getTelephonePortable();
    String getSociete();
    Long getNoSociete();
    LocalDateTime getDateCreation();
    LocalDateTime getDateModification();
    String getSource();
    String getNomSocieteOutlook();
    Boolean getActif();
}