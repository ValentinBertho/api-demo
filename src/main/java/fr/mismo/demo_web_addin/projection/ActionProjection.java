package fr.mismo.demo_web_addin.projection;

public interface ActionProjection {

    Long getActionId();
    String getReference();
    Long getIdInterlocuteur();
    String getTitre();
    String getPriorite();
    String getStatut();

}