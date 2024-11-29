package com.example.projet.ia.Projet_ia_ynov.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@Entity
public class ModelUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

  private String username;
  private String email;
  private Date date_created;
}
