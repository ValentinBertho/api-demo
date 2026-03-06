package fr.mismo.demo_web_addin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "INTERLOC")  // table qui existe réellement en base
public class InterlocuteurEntity {

    @Id
    private Long noInterlo;  // colonne clé primaire réelle
}