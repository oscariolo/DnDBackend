package com.prograweb.dndbackend.domain.dtos;
import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class CampaignUploadDTO {
    private String dungeonMasterId;
    private String name;
    private int maxPlayers;
    private String description;
    private List<CampaingZoneUploadDTO> zones;
}

