package com.example.projet.ia.Projet_ia_ynov.Controller;

import com.example.projet.ia.Projet_ia_ynov.Entity.ModelPromptEntity;
import com.example.projet.ia.Projet_ia_ynov.Repository.ModelPromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/prompt")
public class PromptController {

    private final ModelPromptRepository modelPromptRepository;

    @PostMapping("/update")
    public void updatePrompt(@RequestBody String newPrompt) {
        ModelPromptEntity promptEntity = new ModelPromptEntity();
        promptEntity.setPrompt(newPrompt);
        promptEntity.setUpdateAt(new Date());
        modelPromptRepository.save(promptEntity);
    }

    @GetMapping("/latest")
    public ResponseEntity<String> getLatestPrompt() {
        ModelPromptEntity latestPrompt = modelPromptRepository
                .findTopByOrderByUpdateAtDesc(); // Méthode à ajouter dans le repository
        if (latestPrompt != null) {
            return new ResponseEntity<>(latestPrompt.getPrompt(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Aucun prompt trouvé", HttpStatus.NOT_FOUND);
    }
}