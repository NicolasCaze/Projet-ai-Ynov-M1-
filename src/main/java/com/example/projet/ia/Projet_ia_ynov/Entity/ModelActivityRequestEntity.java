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
public class ModelActivityRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

   private int nb_people;
   private String city;
   private String activity_location;
   private String time_of_day;
   private Date request_date;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private ModelUserEntity user;
}
