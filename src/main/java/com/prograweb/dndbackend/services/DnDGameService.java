package com.prograweb.dndbackend.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.prograweb.dndbackend.domain.dtos.NewCampaignCharacterDTO;
import com.prograweb.dndbackend.domain.dtos.NewPlayerDTO;
import com.prograweb.dndbackend.domain.dtos.GameEvents.LevelUpDTO;
import com.prograweb.dndbackend.domain.models.campaign.CampaignRun;
import com.prograweb.dndbackend.domain.models.campaign.PlayersProgress;
import com.prograweb.dndbackend.domain.repositories.CampaignRunRepository;


@Service
public class DnDGameService {

    @Autowired
    CampaignRunRepository campaignRunRepository;

    // -Los personajes deben tener un tag de habilitados o deshabilitados

    public CampaignRun saveCampaignRun(CampaignRun currentRun){
        return campaignRunRepository.save(currentRun);
    }

    public CampaignRun addNewPlayerToCampaign(NewPlayerDTO newPlayer){
        Optional<CampaignRun> currentRun = campaignRunRepository.findById(newPlayer.gameId);
        if(currentRun.isPresent()){
            CampaignRun runObject = currentRun.get();
            if(runObject.getPlayerIds() == null){
                runObject.setPlayerIds(new ArrayList<String>());
            }
            if(!runObject.getPlayerIds().contains(newPlayer.playerId)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Player already belongs to the given game");
            }
            runObject.getPlayerIds().add(newPlayer.playerId);
            return campaignRunRepository.save(runObject);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Game not found");
        }
    }

    public CampaignRun getCampaignRunById(String gameId){
        Optional<CampaignRun> currentRun = campaignRunRepository.findById(gameId);
        if(currentRun.isPresent()){
            return currentRun.get();
        }
        return null;
    }

    public List<CampaignRun> getCampaignsPlayingByUserId(String userId){
        List<CampaignRun> currentRuns = campaignRunRepository.findByPlayerIds(List.of(userId));
        if(!currentRuns.isEmpty()){
            return currentRuns;
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