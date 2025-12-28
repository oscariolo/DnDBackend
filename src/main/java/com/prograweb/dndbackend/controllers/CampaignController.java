package com.prograweb.dndbackend.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.prograweb.dndbackend.domain.models.campaign.Campaign;
import com.prograweb.dndbackend.services.CampaignService;

import jakarta.validation.Valid;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        return ResponseEntity.ok(campaignService.findAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Campaign>> getCampaignsByUserId(@PathVariable(value = "id") String userId) {
        return ResponseEntity.ok(campaignService.getCampaignByDungeonMasterId(userId));
    }
    

    @PostMapping
    public ResponseEntity<Campaign> addCampaign(@Valid @RequestBody Campaign campaign) {
        campaignService.addCampaign(campaign);
        return ResponseEntity.ok(campaign);
    }


}
