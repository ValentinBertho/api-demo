package fr.mismo.demo_web_addin.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String reference;

    private Long idInterlocuteur;
    private String emailContact;
    private String contactNom;
    private String titre;

    @Column(length = 5000)
    private String description;

    private String statut = "nouvelle";
    private String priorite = "normale";
    private String type;
    private String source;
    private LocalDateTime dateCreation = LocalDateTime.now();
}