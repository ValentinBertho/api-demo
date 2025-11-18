package fr.mismo.demo_web_addin.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Interlocuteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @Column(unique = true)
    private String email;

    private String telephone;
    private String societe;
    private LocalDateTime dateCreation = LocalDateTime.now();
}