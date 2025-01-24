package com.example.projet.ia.Projet_ia_ynov.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@Entity
public class ModelPromptEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;


    @Column(columnDefinition = "TEXT")
    private String prompt;
    @Column(name = "update_at")
    private Date updateAt;
}
