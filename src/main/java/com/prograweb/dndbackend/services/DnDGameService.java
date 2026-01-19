package com.prograweb.dndbackend.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prograweb.dndbackend.domain.dtos.NewCampaignCharacterDTO;
import com.prograweb.dndbackend.domain.dtos.GameEvents.LevelUpDTO;
import com.prograweb.dndbackend.domain.models.campaign.CampaignRun;
import com.prograweb.dndbackend.domain.models.campaign.PlayersProgress;
import com.prograweb.dndbackend.domain.repositories.CampaignRunRepository;


@Service
public class DnDGameService {

    @Autowired
    CampaignRunRepository campaignRunRepository;

    public CampaignRun saveCampaignRun(CampaignRun currentRun){
        return campaignRunRepository.save(currentRun);
    }

    public CampaignRun getCampaignRunById(String gameId){
        Optional<CampaignRun> currentRun = campaignRunRepository.findById(gameId);
        if(currentRun.isPresent()){
            return currentRun.get();
        }
        return null;
    }

    public CampaignRun levelUpCharacter(LevelUpDTO levelUpInfo){
        System.out.println("Leveling up character looking for id " + levelUpInfo.gameId);
        Optional<CampaignRun> currentRun = campaignRunRepository.findById(levelUpInfo.gameId);
        System.out.println(currentRun.toString());
        if(currentRun.isPresent()){
            PlayersProgress gameProgress = currentRun.get().getPlayersProgress();
            gameProgress.levelUpCharacter(levelUpInfo.playerId, levelUpInfo.attributelevelUp); 
            currentRun.get().setPlayersProgress(gameProgress);  
            return campaignRunRepository.save(currentRun.get());
        }
        return null;
    }

    public CampaignRun addNewCharacterToRun(NewCampaignCharacterDTO campaignDto){

        Optional<CampaignRun> currentRun = campaignRunRepository.findById(campaignDto.getGameId());
        if(currentRun.isPresent()){
            CampaignRun runObject = currentRun.get();
            runObject.getAvailableCharacters().put(campaignDto.getPlayerId(), campaignDto.getCharacter());
            return campaignRunRepository.save(runObject);
        }
        return null;
    }


}