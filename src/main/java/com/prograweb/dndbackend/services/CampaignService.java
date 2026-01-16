package com.prograweb.dndbackend.services;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.prograweb.dndbackend.domain.dtos.CampaignUploadDTO;
import com.prograweb.dndbackend.domain.dtos.CampaingZoneUploadDTO;
import com.prograweb.dndbackend.domain.models.ImageMetaData;
import com.prograweb.dndbackend.domain.models.campaign.Campaign;
import com.prograweb.dndbackend.domain.models.campaign.CampaignZone;
import com.prograweb.dndbackend.domain.repositories.CampaignRepository;


//This service is only for static campaign operations, not intended for in game context
@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private ImageDnDService imageService;

    public List<Campaign> findAll() {
        return campaignRepository.findAll();
    }

    public Campaign addCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    public Campaign publishCampaign(CampaignUploadDTO campaignData, MultipartFile[] files){        
        //Para cada zona en campaingData, buscar las imagenes correspondientes en files y subirlas a imageService
        List<CampaingZoneUploadDTO> zones = campaignData.getZones();
    
        Campaign newCampaign = new Campaign(campaignData.getDungeonMasterId(), campaignData.getName(), campaignData.getMaxPlayers(), campaignData.getDescription());

        for(CampaingZoneUploadDTO zone : zones){

            List<ImageMetaData> images = zone.getImages();
            newCampaign.addZone(new CampaignZone(zone.getZoneName(), zone.getDescription(), new ArrayList<String>()));

            for(ImageMetaData imageMetaData : images){
                String originalFileName = imageMetaData.getFileNameReference();

                for(MultipartFile file : files){
                    if(file.getOriginalFilename().equals(originalFileName)){
                        //subir la imagen
                        String imageUrl = imageService.upload(file);
                        //guardar el url en el imageMetaData
                        newCampaign.addImageToZone(zone.getZoneName(), imageUrl);
                        break;
                    }
                }
            }
        }
        newCampaign = campaignRepository.save(newCampaign);
        return newCampaign;
    }

    public List<Campaign> getCampaignByDungeonMasterId(String dungeonMasterId) {
        return campaignRepository.findByDungeonMasterId(dungeonMasterId);
    }
    
}
