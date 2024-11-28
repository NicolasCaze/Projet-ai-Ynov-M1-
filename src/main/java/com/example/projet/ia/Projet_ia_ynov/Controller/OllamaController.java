package com.example.projet.ia.Projet_ia_ynov.Controller;

import com.example.projet.ia.Projet_ia_ynov.Service.OllamaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ia")
public class OllamaController {

    private final OllamaService ollamaService;

    @PostMapping("/ollama")
    public String ollama(@RequestParam(required = false) String message) {
        if (message == null || message.trim().isEmpty()) {
            return "Le message ne doit pas être vide.";
        }
        return ollamaService.generateOllama(message);
    }
}
