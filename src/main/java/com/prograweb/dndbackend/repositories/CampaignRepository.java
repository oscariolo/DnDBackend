package com.prograweb.dndbackend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.prograweb.dndbackend.models.campaign.Campaign;

public interface CampaignRepository extends MongoRepository<Campaign, String> {
    
}
