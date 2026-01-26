package com.prograweb.dndbackend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.prograweb.dndbackend.domain.dtos.NewCharacterToCampaignRequest;
import com.prograweb.dndbackend.domain.dtos.NewCampaignRunRequest;
import com.prograweb.dndbackend.domain.models.campaign.CampaignRun;
import com.prograweb.dndbackend.domain.models.characters.CharacterBase;
import com.prograweb.dndbackend.domain.repositories.CampaignRunRepository;
import com.prograweb.dndbackend.domain.repositories.CharacterRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignRunController {

    @Autowired
    private CampaignRunRepository campaignRunRepository;

    @Autowired
    private CharacterRepository characterRepository;

    @PostMapping("/game/character")
    public ResponseEntity<?> addCharacterToCampaign(@RequestBody NewCharacterToCampaignRequest req) {
        if (req.getGameId() == null || req.getCharacterId() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Missing gameId or characterId"));
        }

        Optional<CampaignRun> runOpt = campaignRunRepository.findById(req.getGameId());
        Optional<CharacterBase> charOpt = characterRepository.findById(req.getCharacterId());
        if (charOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Character not found"));
        }

        CharacterBase character = charOpt.get();

        CampaignRun run;
        if (runOpt.isEmpty()) {
            run = new CampaignRun();
            run.setId(req.getGameId());
            run.setBaseCampaignId(null);
            run.setDungeonMasterId(null);
            run.setPlayerIds(new java.util.ArrayList<>());
            run.setPlayersProgress(null);
            run.setAvailableCharacters(new HashMap<>());
        } else {
            run = runOpt.get();
            if (run.getAvailableCharacters() == null) {
                run.setAvailableCharacters(new HashMap<>());
            }
        }
        run.getAvailableCharacters().put(req.getCharacterId(), character);

        CampaignRun saved = campaignRunRepository.save(run);

        return ResponseEntity.ok(saved);
    }

    @PostMapping("/game")
    public ResponseEntity<?> createCampaignRun(@RequestBody NewCampaignRunRequest req) {
        if (req.getGameId() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Missing gameId"));
        }

        CampaignRun run = new CampaignRun();
        run.setId(req.getGameId());
        run.setBaseCampaignId(req.getBaseCampaignId());
        run.setDungeonMasterId(req.getDungeonMasterId());
        run.setPlayerIds(req.getPlayerIds() == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(req.getPlayerIds()));
        run.setPlayersProgress(null);
        run.setAvailableCharacters(new HashMap<>());

        CampaignRun saved = campaignRunRepository.save(run);
        return ResponseEntity.status(201).body(saved);
    }

}
