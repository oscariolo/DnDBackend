package com.prograweb.dndbackend.domain.models.campaign;

import java.util.Map;

import com.prograweb.dndbackend.domain.models.characters.PlayableCharacter;

import lombok.Data;

@Data
public class CampaignProgress {
    
    private final Map<String,PlayableCharacter> playersProgress;

    private String currentZoneName;

    public void levelUpCharacter(String playerIndex, Map<String,Integer> levelUpMap){
        PlayableCharacter character = playersProgress.get(playerIndex);
        Map<String,Integer> currentAttributes = character.getAttributes();

        for (String attributeName : levelUpMap.keySet() ) {
            Integer newAtrributeValue = levelUpMap.get(attributeName) + currentAttributes.get(attributeName);
            currentAttributes.put(attributeName, newAtrributeValue);
        }

        character.setAttributes(currentAttributes);
        playersProgress.put(playerIndex, character);
    }

}
