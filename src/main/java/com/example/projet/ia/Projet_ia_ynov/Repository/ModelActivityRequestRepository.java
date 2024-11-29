package com.example.projet.ia.Projet_ia_ynov.Repository;

import com.example.projet.ia.Projet_ia_ynov.Entity.ModelActivityRequestEntity;
import com.example.projet.ia.Projet_ia_ynov.Entity.ModelActivitySuggestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModelActivityRequestRepository extends JpaRepository<ModelActivityRequestEntity, Long> {
}
