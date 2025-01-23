package com.example.projet.ia.Projet_ia_ynov.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
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
    private boolean hasChildren;
    private boolean weekend; // Modifie le nom du champ pour être cohérent
    private boolean free;    // Idem ici

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ModelUserEntity user;
}
