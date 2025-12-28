package com.prograweb.dndbackend.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.prograweb.dndbackend.models.characters.CharacterBase;
import com.prograweb.dndbackend.models.characters.PlayableCharacter;

import org.springframework.data.mongodb.repository.Query;

public interface CharacterRepository extends MongoRepository<CharacterBase,String> {
    @Query("{ '_class' : 'com.prograweb.dndbackend.models.characters.PlayableCharacter' }")
    List<PlayableCharacter> findAllPlayableCharacters();
}