package com.prograweb.dndbackend.domain.models.campaign;


import org.springframework.data.mongodb.core.mapping.Document;


import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Document(collection="campaignRuns")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CampaignRun {

    @Id
    private String id;

    private String campaignId;
    
    private CampaignProgress progress;

}
