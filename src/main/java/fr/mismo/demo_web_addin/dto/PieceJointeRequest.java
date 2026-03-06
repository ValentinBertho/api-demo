package fr.mismo.demo_web_addin.dto;

import lombok.Data;

import java.util.List;

@Data
public class PieceJointeRequest {

    private String emailFrom;
    private String emailSubject;
    private List<AttachmentDto> attachments;

}