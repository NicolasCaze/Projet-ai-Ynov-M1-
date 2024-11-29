package com.example.projet.ia.Projet_ia_ynov.Controller;

import com.example.projet.ia.Projet_ia_ynov.Service.FileReadingService;
import com.example.projet.ia.Projet_ia_ynov.Service.OllamaService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;
import org.springframework.ai.ollama.OllamaChatModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ia")
public class OllamaController {

    private final FileReadingService fileReadingService;
    private final OllamaService ollamaService;
    private final OllamaChatModel chatModel;

    @PostMapping("/ollama")
    public String ollama(@RequestParam(required = false) String question,
                         @RequestParam(required = false) Integer nbPerson,
                         @RequestParam(required = false) Boolean activity_location,
                         @RequestParam(required = false) String ville,
                         @RequestParam(required = false) Boolean time_of_day) {

        String prompt = fileReadingService.readInternalFileString("Prompts/prompt");
        String userDetails = String.format(
                "Voici les détails fournis par l'utilisateur :%n- Nombre de personnes : %s%n- Ville : %s%n- Moment de la journée : %s%n- Activité d'intérieur/extérieur : %s",
                nbPerson != null ? nbPerson : "non spécifié",
                ville != null ? ville : "non spécifiée",
                time_of_day != null ? (time_of_day ? "jour" : "nuit") : "non spécifié",
                activity_location != null ? (activity_location ? "extérieur" : "intérieur") : "non spécifiée");

        String fullPrompt = userDetails + System.lineSeparator() + prompt;
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage("<start_of_turn" + fullPrompt + "end_of_turn"));
        messages.add(new UserMessage("<start_of_turn" + (question != null ? question : "Pas de question fournie") + "end_of_turn"));

        Prompt promptToSend = new Prompt(messages);
        Flux<ChatResponse> chatResponses = chatModel.stream(promptToSend);
        String message = Objects.requireNonNull(chatResponses.collectList().block()).stream()
                .map(response -> response.getResult().getOutput().getContent())
                .collect(Collectors.joining(""));

        return message;
    }
}
