package com.prograweb.dndbackend.domain.repositories;

import com.prograweb.dndbackend.domain.models.campaign.CampaignRun;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CampaignRunRepository extends MongoRepository<CampaignRun,String> {
    
}
