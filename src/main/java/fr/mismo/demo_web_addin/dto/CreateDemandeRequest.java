package fr.mismo.demo_web_addin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Corps de la requête pour créer une demande (incident) dans ATHENEO")
public class CreateDemandeRequest {

    @Schema(description = "Adresse e-mail de l'expéditeur / contact", example = "client@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Nom complet du contact (expéditeur)", example = "Jean Dupont", requiredMode = Schema.RequiredMode.REQUIRED)
    private String contactName;

    @Schema(description = "Objet de l'e-mail, utilisé comme intitulé de la demande", example = "Panne sur le module de facturation", requiredMode = Schema.RequiredMode.REQUIRED)
    private String subject;

    @Schema(description = "Corps de l'e-mail, utilisé comme description détaillée de la demande", example = "Depuis ce matin, impossible d'accéder à la liste des factures.")
    private String description;

    @Schema(description = "Source de la demande", example = "EMAIL", allowableValues = {"EMAIL", "TEL", "AUTRE"})
    private String source;

    @Schema(description = "Priorité de la demande", example = "HAUTE", allowableValues = {"BASSE", "NORMALE", "HAUTE", "URGENTE"})
    private String priority;

    @Schema(description = "Type de la demande", example = "INCIDENT", allowableValues = {"INCIDENT", "DEMANDE", "EVOLUTION"})
    private String type;

}
