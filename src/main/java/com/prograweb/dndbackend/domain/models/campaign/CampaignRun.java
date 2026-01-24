package com.prograweb.dndbackend.domain.models.campaign;
import org.springframework.data.mongodb.core.mapping.Document;
import com.prograweb.dndbackend.domain.models.characters.PlayableCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Document(collection="campaignRuns")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRun {

    @Id
    private String id;

    private String baseCampaignId;

    private String dungeonMasterId;

    private ArrayList<String> playerIds;
    
    private PlayersProgress playersProgress;

    private Map<String, Object> availableCharacters;


    public List<PlayableCharacter> getPlayableCharacters(){
        if (availableCharacters == null) {
            return new ArrayList<>();
        }

        List<PlayableCharacter> playableCharacters = availableCharacters.values().stream()
        .filter(character -> character instanceof PlayableCharacter)
        .map(character -> (PlayableCharacter) character)
        .toList();

        return playableCharacters;

    }

}
