package com.prograweb.dndbackend.domain.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.prograweb.dndbackend.domain.models.campaign.Campaign;

public interface CampaignRepository extends MongoRepository<Campaign, String> {
    
}
