package com.example.projet.ia.Projet_ia_ynov.Controller;


import com.example.projet.ia.Projet_ia_ynov.Dto.ActivityRequestDTO;
import com.example.projet.ia.Projet_ia_ynov.Repository.ModelActivitySuggestionRepository;
import org.springframework.web.bind.annotation.*;
import com.example.projet.ia.Projet_ia_ynov.Service.FileReadingService;
import com.example.projet.ia.Projet_ia_ynov.Service.OllamaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ai.ollama.OllamaChatModel;


@RestController
@RequiredArgsConstructor
@RequestMapping("/ia")
public class OllamaController {

    private final OllamaService ollamaService;

    @PostMapping("/ollama")
    public String ollama(@RequestBody ActivityRequestDTO activityRequestDTO) {

        return ollamaService.generateOllama(
                activityRequestDTO.nb_people(),
                activityRequestDTO.activity_location(),
                activityRequestDTO.city(),
                activityRequestDTO.time_of_day(),
                activityRequestDTO.hasChildren(),
                activityRequestDTO.isWeekend(),
                activityRequestDTO.isFree()
        );
    }

}
