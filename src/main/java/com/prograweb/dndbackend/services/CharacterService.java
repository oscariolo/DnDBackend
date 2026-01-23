package com.prograweb.dndbackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prograweb.dndbackend.domain.models.characters.CharacterBase;
import com.prograweb.dndbackend.domain.models.characters.EnemyCharacter;
import com.prograweb.dndbackend.domain.models.characters.PlayableCharacter;
import com.prograweb.dndbackend.domain.repositories.CharacterRepository;


@Service
public class CharacterService {
    @Autowired
    private CharacterRepository characterRepository;

    public CharacterBase addCharacter(CharacterBase character) {
        return characterRepository.save(character);
    }

    public List<CharacterBase> getAllCharacters() {
        return characterRepository.findAll();
    }

    public List<PlayableCharacter> getAllPlayableCharacters() {
        return characterRepository.findAllPlayableCharacters();
    }

    public List<EnemyCharacter> getAllEnemyCharacters() {
        return characterRepository.findAllEnemyCharacters();
    }

    public List<CharacterBase> gettCharactersByUserId(String userId) {
        return characterRepository.findAll().stream().filter(c -> c.getCreatorId().equals(userId)).toList();
    }
    
    public List<PlayableCharacter> getCharactersByUserId(String userId) {
        return characterRepository.findAll().stream()
            .filter(c -> c instanceof PlayableCharacter && c.getCreatorId().equals(userId))
            .map(c -> (PlayableCharacter) c)
            .toList();
    }


}