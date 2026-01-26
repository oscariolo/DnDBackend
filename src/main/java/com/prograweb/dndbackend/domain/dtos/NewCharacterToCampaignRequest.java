package com.prograweb.dndbackend.domain.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewCharacterToCampaignRequest {
    private String gameId;
    private String characterId;
    private String playerId;
}
