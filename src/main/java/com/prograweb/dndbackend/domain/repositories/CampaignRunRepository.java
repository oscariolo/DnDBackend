package com.prograweb.dndbackend.domain.repositories;

import com.prograweb.dndbackend.domain.models.campaign.CampaignRun;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface CampaignRunRepository extends MongoRepository<CampaignRun,String> {
    
   List<CampaignRun> findByPlayerIds(List<String> playerIds);

}
