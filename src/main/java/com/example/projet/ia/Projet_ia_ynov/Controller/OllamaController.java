package com.example.projet.ia.Projet_ia_ynov.Controller;

import com.example.projet.ia.Projet_ia_ynov.Dto.ActivityRequestDTO;
import com.example.projet.ia.Projet_ia_ynov.Service.OllamaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ia")
public class OllamaController {

    private final OllamaService ollamaService;

    @PostMapping("/ollama")
    public String ollama(@RequestBody String message) {
        if (message == null || message.trim().isEmpty()) {
            return "Le message ne doit pas être vide.";
        }
        return ollamaService.generateOllama(message);
    }
    @PostMapping("/activite")
    public String reponseActivite(@RequestBody ActivityRequestDTO activityRequestDTO){
        return ollamaService.generateResponse(activityRequestDTO.nb_people(),
                activityRequestDTO.city(),
                activityRequestDTO.activity_location(),
                activityRequestDTO.time_of_day());
    }
}
