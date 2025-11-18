package fr.mismo.demo_web_addin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class PieceJointe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomFichier;
    private Long taille;
    private String typeMime;
    private String idOutlook;
    private String emailSource;
    private String sujetMail;
    private LocalDateTime dateAjout = LocalDateTime.now();
}