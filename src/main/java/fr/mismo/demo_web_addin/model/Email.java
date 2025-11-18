package fr.mismo.demo_web_addin.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String expediteur;
    private String expediteurNom;
    private String sujet;

    @Column(length = 5000)
    private String contenu;

    private LocalDateTime dateReception;
    private LocalDateTime dateCreation = LocalDateTime.now();
}
