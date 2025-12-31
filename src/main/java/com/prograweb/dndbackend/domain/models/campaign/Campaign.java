package com.prograweb.dndbackend.domain.models.campaign;
import java.util.ArrayList;
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

    private final String description;

    private ArrayList<CampaignZone> campaignZones = new ArrayList<>();

    public void addZone(CampaignZone zone) {
        this.campaignZones.add(zone);
    }
    
    public void addImageToZone(String zoneName, String imageUrl) {
        for (CampaignZone zone : campaignZones) {
            if (zone.getZoneName().equals(zoneName)) {
                zone.getZoneImgUrls().add(imageUrl);
                return;
            }
        }
    }
    
}

