package com.example.projet.ia.Projet_ia_ynov.Service;

import com.example.projet.ia.Projet_ia_ynov.Entity.ModelActivityRequestEntity;
import com.example.projet.ia.Projet_ia_ynov.Entity.ModelActivitySuggestionEntity;
import com.example.projet.ia.Projet_ia_ynov.Repository.ModelActivityRequestRepository;
import com.example.projet.ia.Projet_ia_ynov.Repository.ModelActivitySuggestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OllamaServiceImpl implements OllamaService {

    private final ChatModel chatModel;
    private final ModelActivityRequestRepository modelActivityRequestRepository;
    private final ModelActivitySuggestionRepository modelActivitySuggestionRepository;
    private final ReadingTableService readingTableService;

    @Override
    public String generateOllama(
            Integer nbPerson,
            String activity_location,
            String city,
            String time_of_day,
            boolean hasChildren,
            boolean isWeekend,
            boolean isFree
    ) {
        // Récupérer le prompt depuis la base de données
        String prompt = readingTableService.getLatestPrompt();
        if (prompt == null || prompt.isEmpty()) {
            prompt = "Prompt par défaut.";
        }

        // Construire les détails de l'utilisateur
        String userDetails = String.format(
                "Voici les détails fournis par l'utilisateur :%n"
                        + "- Nombre de personnes : %s%n"
                        + "- Ville : %s%n"
                        + "- Moment de la journée : %s%n"
                        + "- Activité d'intérieur/extérieur : %s%n"
                        + "- Avec enfants : %s%n"
                        + "- Weekend : %s%n"
                        + "- Activités gratuites : %s",
                nbPerson != null ? nbPerson : "non spécifié",
                city != null ? city : "non spécifiée",
                time_of_day != null ? time_of_day : "non spécifié",
                activity_location != null ? activity_location : "non spécifiée",
                hasChildren ? "oui" : "non",
                isWeekend ? "oui" : "non",
                isFree ? "oui" : "non"
        );

        String priceFilterInstruction = isFree
                ? "Je veux uniquement des activités gratuites."
                : "Je veux uniquement des activités payantes.";
        userDetails += System.lineSeparator() + priceFilterInstruction;

        String fullPrompt = userDetails + System.lineSeparator() + prompt;

        // Préparer les messages pour l'IA
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage("<start_of_turn>" + fullPrompt + "<end_of_turn>"));
        messages.add(new UserMessage("<start_of_turn>" + fullPrompt + "<end_of_turn>"));

        // Envoyer le prompt au modèle IA
        Prompt promptToSend = new Prompt(messages);
        Flux<ChatResponse> chatResponses = chatModel.stream(promptToSend);
        String message = Objects.requireNonNull(chatResponses.collectList().block()).stream()
                .map(response -> response.getResult().getOutput().getContent())
                .collect(Collectors.joining(""));

        // Enregistrer la requête dans la base de données
        ModelActivityRequestEntity requestEntity = new ModelActivityRequestEntity();
        requestEntity.setNb_people(nbPerson != null ? nbPerson : 0);
        requestEntity.setCity(city);
        requestEntity.setActivity_location(activity_location);
        requestEntity.setTime_of_day(time_of_day);
        requestEntity.setRequest_date(new Date());
        requestEntity.setHasChildren(hasChildren);
        requestEntity.setWeekend(isWeekend);
        requestEntity.setFree(isFree);
        modelActivityRequestRepository.save(requestEntity);

        // Enregistrer la suggestion générée dans la base de données
        ModelActivitySuggestionEntity suggestionEntity = new ModelActivitySuggestionEntity();
        suggestionEntity.setSuggested_activites(message);
        suggestionEntity.setGenerated_at(new Date());
        suggestionEntity.setModelActivityRequestEntity(requestEntity);
        modelActivitySuggestionRepository.save(suggestionEntity);

        return message;
    }

    @Override
    public String generateResponse(int nb_people, String city, String activity_location, String time_of_day) {
        // Implémentation simple de generateResponse
        return String.format(
                "Nombre de personnes : %d, Ville : %s, Activité : %s, Moment de la journée : %s",
                nb_people, city, activity_location, time_of_day
        );
    }
}
