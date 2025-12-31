package com.prograweb.dndbackend.domain.models.campaign;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CampaignZone {
    private String zoneName;
    private String description;
    private ArrayList<String> zoneImgUrls;
}