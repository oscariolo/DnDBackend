package com.prograweb.dndbackend.domain.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.prograweb.dndbackend.domain.models.campaign.Campaign;
import java.util.List;


public interface CampaignRepository extends MongoRepository<Campaign, String> {
    @Query(
        "{ 'dungeonMasterId' : ?0 }"
    )
    List<Campaign> findByDungeonMasterId(String dungeonMasterId);
    
}
