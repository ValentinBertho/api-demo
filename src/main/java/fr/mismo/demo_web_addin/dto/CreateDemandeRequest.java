package fr.mismo.demo_web_addin.dto;

import lombok.Data;

@Data
public class CreateDemandeRequest {

    private String email;
    private String contactName;
    private String subject;
    private String description;
    private String source;
    private String priority;
    private String type;

}
