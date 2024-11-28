package com.example.projet.ia.Projet_ia_ynov.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.Date;

@Data
@Getter
@Setter
@Entity
public class ModelActivitySuggestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    private String suggested_activites;
    private Date generated_at;

    @OneToOne
    @JoinColumn(name = "modelActivityRequestEntity_id")
    private ModelActivityRequestEntity modelActivityRequestEntity;
}
