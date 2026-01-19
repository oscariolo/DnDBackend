package com.prograweb.dndbackend.domain.dtos;

import com.prograweb.dndbackend.domain.models.characters.CharacterBase;

import lombok.Getter;

@Getter
public class NewCampaignCharacterDTO {
    private String gameId;
    private CharacterBase character;
    private String playerId;
}
