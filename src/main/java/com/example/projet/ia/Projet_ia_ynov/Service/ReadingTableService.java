package com.example.projet.ia.Projet_ia_ynov.Service;

import com.example.projet.ia.Projet_ia_ynov.Entity.ModelPromptEntity;
import com.example.projet.ia.Projet_ia_ynov.Repository.ModelPromptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadingTableService {

    private final ModelPromptRepository modelPromptRepository;

    public String getLatestPrompt() {
        // Récupérer le prompt le plus récent (vous pouvez affiner selon vos besoins)
        return modelPromptRepository.findAll().stream()
                .max((p1, p2) -> p1.getUpdateAt().compareTo(p2.getUpdateAt()))
                .map(ModelPromptEntity::getPrompt)
                .orElse("Tu es une intelligence artificielle intégrée dans une application web conçue pour aider des personnes ou des groupes d'amis à trouver des activités adaptées à leurs besoins. Tes réponses doivent être limitées à 200 caractères.\n" +
                        "\n" +
                        "Voici les détails fournis par l'utilisateur :\n" +
                        "- Nombre de personnes : <nbPerson>\n" +
                        "- Ville : <ville>\n" +
                        "- Moment de la journée : <time_of_day>\n" +
                        "- Activité d'intérieur/extérieur : <activity_location>\n" +
                        "\n" +
                        "En fonction de ces informations :\n" +
                        "1. Propose plusieurs activités adaptées, spécifiques et pertinentes selon la ville ou ils se trouvent. Pour chaque activité, calcule le prix total : **prix total = (nombre de personnes x prix par personne)**. N'affiche pas le calcul dans ta reponse. Donne seulement le prix.\n" +
                        "\n" +
                        "Si les informations sont insuffisantes (s'il ne précise pas le nombre de personnes, la ville, etc.), demande des précisions à l'utilisateur.\n"); // Retourner un prompt par défaut si aucun n'est trouvé
    }
}
