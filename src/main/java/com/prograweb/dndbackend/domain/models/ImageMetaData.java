package com.prograweb.dndbackend.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ImageMetaData {
    private String fileNameReference;
}
