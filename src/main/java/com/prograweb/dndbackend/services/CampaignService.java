package com.prograweb.dndbackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prograweb.dndbackend.domain.models.campaign.Campaign;
import com.prograweb.dndbackend.domain.repositories.CampaignRepository;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    public List<Campaign> findAll() {
        return campaignRepository.findAll();
    }

    public Campaign addCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    public List<Campaign> getCampaignByDungeonMasterId(String dungeonMasterId) {
        return campaignRepository.findByDungeonMasterId(dungeonMasterId);
    }

    
}
