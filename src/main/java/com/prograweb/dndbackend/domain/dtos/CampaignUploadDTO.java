package com.prograweb.dndbackend.domain.dtos;
import java.util.List;

import lombok.Data;

@Data
public class CampaignUploadDTO {

    private final String dungeonMasterId;

    private final String name;

    private final int maxPlayers;

    private String description;

    private List<CampaingZoneUploadDTO> zones;
    
}

