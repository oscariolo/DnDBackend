package com.prograweb.dndbackend.controllers;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.prograweb.dndbackend.domain.dtos.CampaignUploadDTO;
import com.prograweb.dndbackend.domain.models.campaign.Campaign;
import com.prograweb.dndbackend.services.CampaignService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;


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
    
    //Endpoint dedicated to upload a campaign meaning with images attached to the request
    @PostMapping(path = "/upload",
        consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }
    )
    public ResponseEntity<Campaign> uploadCampaign(@RequestPart("campaignDetails") CampaignUploadDTO campaignDetails, @RequestPart("files") MultipartFile[] files) {
        Campaign campaign = campaignService.publishCampaign(campaignDetails, files);
        return ResponseEntity.ok(campaign);
    }
    

    @PostMapping
    public ResponseEntity<Campaign> addCampaign(@Valid @RequestBody Campaign campaign) {
        campaignService.addCampaign(campaign);
        return ResponseEntity.ok(campaign);
    }


}
