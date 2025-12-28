package com.prograweb.dndbackend.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.prograweb.dndbackend.domain.models.campaign.Campaign;
import com.prograweb.dndbackend.domain.repositories.CampaignRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {

    @Autowired
    private CampaignRepository campaignRepository;

    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        return ResponseEntity.ok(campaignRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<String> addCampaign(@RequestBody Campaign campaign) {
        campaignRepository.save(campaign);
        return ResponseEntity.ok("Campaign created successfully");
    }
}
