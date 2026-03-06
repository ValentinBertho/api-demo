package fr.mismo.demo_web_addin.dto;

import lombok.Data;

@Data
public class CreateActionRequest {

    private String email;
    private String contactName;
    private String title;
    private String description;
    private String type;
    private String priority;
    private String status;
    private String source;

}
