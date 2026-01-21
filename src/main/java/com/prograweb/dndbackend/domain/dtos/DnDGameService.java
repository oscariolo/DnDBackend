package com.prograweb.dndbackend.domain.dtos;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.prograweb.dndbackend.domain.dtos.GameEvents.LevelUpDTO;
import com.prograweb.dndbackend.domain.models.campaign.CampaignRun;
import com.prograweb.dndbackend.domain.models.campaign.PlayersProgress;
import com.prograweb.dndbackend.domain.repositories.CampaignRunRepository;


@Service
//TODO dndgameservice se utiliza unicamente para eventos en juego que deban ser persistidos
//guardar progreso de los personajes de los jugadores, guardar estado de la campaña (zona actual)
public class DnDGameService {

    @Autowired
    CampaignRunRepository campaignRunRepository;

    public CampaignRun saveCampaignRun(CampaignRun currentRun){
        return campaignRunRepository.save(currentRun);
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


}