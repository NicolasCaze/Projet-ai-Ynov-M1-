package com.example.projet.ia.Projet_ia_ynov.Service;

import org.springframework.stereotype.Service;

@Service
public interface OllamaService {
    String generateOllama(String message);
    String generateResponse(int nb_people, String city, String activity_location, String time_of_day);
}
