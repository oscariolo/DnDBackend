package com.prograweb.dndbackend.controllers;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.prograweb.dndbackend.domain.dtos.CampaignUploadDTO;
import com.prograweb.dndbackend.domain.dtos.NewCampaignCharacterDTO;
import com.prograweb.dndbackend.domain.dtos.NewPlayerDTO;
import com.prograweb.dndbackend.domain.dtos.GameEvents.LevelUpDTO;
import com.prograweb.dndbackend.domain.models.campaign.Campaign;
import com.prograweb.dndbackend.domain.models.campaign.CampaignRun;
import com.prograweb.dndbackend.services.CampaignService;
import com.prograweb.dndbackend.services.DnDGameService;
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
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private DnDGameService gameService;

    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        return ResponseEntity.ok(campaignService.findAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Campaign>> getCampaignsByUserId(@PathVariable(value = "id") String userId) {
        return ResponseEntity.ok(campaignService.getCampaignByDungeonMasterId(userId));
    }
    
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

    @PostMapping("/game")
    public CampaignRun createNewCampaignRun(@RequestBody CampaignRun newRun) {
        return gameService.saveCampaignRun(newRun);
    }

    @PutMapping("/game/levelup")
    public ResponseEntity<CampaignRun> levelUpCharacter(@RequestBody LevelUpDTO levelUpDto) {
        CampaignRun updateLevel = gameService.levelUpCharacter(levelUpDto);
        return ResponseEntity.ok(updateLevel);
    }

    @PostMapping("/game/character")
    public ResponseEntity<CampaignRun> postGameCharacter(@RequestBody NewCampaignCharacterDTO newcharacterDto) {
        CampaignRun campaignRun = gameService.addNewCharacterToRun(newcharacterDto);
        if(campaignRun == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(campaignRun);
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<CampaignRun> getCampaignRun(@PathVariable String gameId) {
        CampaignRun campaignRun = gameService.getCampaignRunById(gameId);
        return ResponseEntity.ok(campaignRun);
    }
    
    @GetMapping("/game/user/playing/{userId}")
    public ResponseEntity<List<CampaignRun>> getCampaignsPlayingByUserId(@PathVariable String userId) {
        List<CampaignRun> campaignRuns = gameService.getCampaignsPlayingByUserId(userId);
        return ResponseEntity.ok(campaignRuns);
    }

    @PostMapping("/game/user")
    public ResponseEntity<String> postNewPlayerToCampaign(@RequestBody NewPlayerDTO newPlayerentity) {
        gameService.addNewPlayerToCampaign(newPlayerentity);
        return ResponseEntity.ok("Jugador agregado a la campaña exitosamente");
    }

    @GetMapping("/{campaignId}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable String campaignId) {
        Campaign campaign = campaignService.findById(campaignId);
        if (campaign == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(campaign);
    }

}
