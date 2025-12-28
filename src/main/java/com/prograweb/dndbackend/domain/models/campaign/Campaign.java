package com.prograweb.dndbackend.domain.models.campaign;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Document(collection = "campaigns")
@Getter@Setter
@RequiredArgsConstructor
public class Campaign {

    @Id
    private String id;

    private final String dungeonMasterId;

    private final String name;

    private final int maxPlayers;

    private String description;

    private List<CampaignZone> zones;
    
}

