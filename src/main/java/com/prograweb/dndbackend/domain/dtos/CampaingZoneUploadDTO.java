package com.prograweb.dndbackend.domain.dtos;

import java.util.List;

import com.prograweb.dndbackend.domain.models.ImageMetaData;

import lombok.Data;

@Data
public class CampaingZoneUploadDTO {
    
    private String zoneName;

    private String description;

    private List<ImageMetaData> images;

    
}
