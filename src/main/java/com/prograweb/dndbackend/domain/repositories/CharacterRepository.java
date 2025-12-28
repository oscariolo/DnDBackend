package com.prograweb.dndbackend.domain.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.prograweb.dndbackend.domain.models.characters.CharacterBase;
import com.prograweb.dndbackend.domain.models.characters.PlayableCharacter;

public interface CharacterRepository extends MongoRepository<CharacterBase,String> {
    @Query(
        "{ '_class' : 'PlayableCharacter' }"
    )
    List<PlayableCharacter> findAllPlayableCharacters();
}