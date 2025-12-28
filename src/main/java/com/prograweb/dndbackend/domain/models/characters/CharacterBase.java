package com.prograweb.dndbackend.domain.models.characters;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Document(collection = "characters")
@Getter
@Setter
@RequiredArgsConstructor

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "characterType"
)

@JsonSubTypes({
    @JsonSubTypes.Type(value = PlayableCharacter.class, name = "playable"),
    @JsonSubTypes.Type(value = EnemyCharacter.class, name = "enemy")
})

public abstract class CharacterBase {

    private final String id;

    private final String creatorId;

    private final String name;

    private String characterDescription;

}
