package com.prograweb.dndbackend.domain.models.campaign;

import java.util.List;
import lombok.Data;

@Data
public class CampaignZone {
    private String zoneName;
    private String description;
    private List<String> zoneImgUrls;
}