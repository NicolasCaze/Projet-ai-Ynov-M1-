package com.example.projet.ia.Projet_ia_ynov.Service;

import com.example.projet.ia.Projet_ia_ynov.Entity.ModelActivityRequestEntity;
import com.example.projet.ia.Projet_ia_ynov.Repository.ModelActivityRequestRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class OllamaServiceImpl implements OllamaService{

    private final ChatModel chatModel;
    private final ModelActivityRequestRepository modelActivityRequestRepository;

    public String generateOllama(String message){
        ChatResponse response = chatModel.call(
                new Prompt(
                        message,
                        OllamaOptions.create()
                                .withModel("llama3.2:1b")
                                .withTemperature(0.4)
                ));
        return response.getResult().getOutput().getContent();
    }
    public String generateResponse(int nb_people,
                                                       String city,
                                                       String activity_location,
                                                       String time_of_day){

        String message = String.format("Pour %d personnes à %s, activité située %s durant la période %s.",
                nb_people, city, activity_location, time_of_day);

        ChatResponse response = chatModel.call(
                new Prompt(
                        message,
                        OllamaOptions.create()
                                .withModel("llama3.2:1b")
                                .withTemperature(0.4)
                ));

        //Ajout des informations du formulaire dans la base de données
        ModelActivityRequestEntity modelActivityRequestEntity = new ModelActivityRequestEntity();
        modelActivityRequestEntity.setNb_people(nb_people);
        modelActivityRequestEntity.setCity(city);
        modelActivityRequestEntity.setActivity_location(activity_location);
        modelActivityRequestEntity.setTime_of_day(time_of_day);
        modelActivityRequestEntity.setRequest_date(new Date());
        modelActivityRequestRepository.save(modelActivityRequestEntity);
        //affichage de la réponse de l'ia
        return response.getResult().getOutput().getContent();
    }

}
