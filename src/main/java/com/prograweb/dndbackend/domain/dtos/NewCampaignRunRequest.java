package com.prograweb.dndbackend.domain.dtos;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewCampaignRunRequest {
    private String gameId;
    private String baseCampaignId;
    private String dungeonMasterId;
    private List<String> playerIds;
}
