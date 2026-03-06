package fr.mismo.demo_web_addin.dto;

import lombok.Data;

@Data
public class AttachmentDto {

    private String id;
    private String name;
    private String contentType;
    private long size;

}