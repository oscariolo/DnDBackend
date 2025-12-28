package com.prograweb.dndbackend.models.characters;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Document(collection = "characters")
@Getter
@Setter
@RequiredArgsConstructor

public abstract class CharacterBase {

    private final String id;

    private final String creatorId;

    private final String name;

    private String characterDescription;
    
}
