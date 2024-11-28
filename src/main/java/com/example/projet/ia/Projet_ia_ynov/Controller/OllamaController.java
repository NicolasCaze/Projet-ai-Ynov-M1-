package com.example.projet.ia.Projet_ia_ynov.Controller;

import com.example.projet.ia.Projet_ia_ynov.Service.OllamaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ia")
public class OllamaController {

    private final OllamaService ollamaService;

    @RequestMapping("/ollama")
    public String ollama(String message){
        return ollamaService.generateOllama(message);
    }
}
