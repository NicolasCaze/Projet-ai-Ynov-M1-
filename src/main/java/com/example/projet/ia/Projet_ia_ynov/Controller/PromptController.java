package com.example.projet.ia.Projet_ia_ynov.Controller;

import com.example.projet.ia.Projet_ia_ynov.Entity.ModelPromptEntity;
import com.example.projet.ia.Projet_ia_ynov.Repository.ModelPromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        promptEntity.setUpdate_at(new Date());
        modelPromptRepository.save(promptEntity);
    }
}