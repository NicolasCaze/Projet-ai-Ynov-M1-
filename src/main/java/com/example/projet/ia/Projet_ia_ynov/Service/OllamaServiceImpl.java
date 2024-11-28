package com.example.projet.ia.Projet_ia_ynov.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OllamaServiceImpl implements OllamaService{

    private final ChatModel chatModel;

    public String generateOllama(String message){
        ChatResponse response = chatModel.call(
                new Prompt(
                        message,
                        OllamaOptions.create()
                                .withModel("llama3")
                                .withTemperature(0.4)
                ));
        return response.getResult().getOutput().getContent();
    }

}
