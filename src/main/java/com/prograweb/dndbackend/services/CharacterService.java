package com.prograweb.dndbackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prograweb.dndbackend.domain.models.characters.CharacterBase;
import com.prograweb.dndbackend.domain.models.characters.PlayableCharacter;
import com.prograweb.dndbackend.domain.repositories.CharacterRepository;

//TODO: Agregar un personaje debe especificarse el tipo que es
//Segun ese tipo se mapea a la subclase correspondiente y se guarda en la base de datos

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

    public List<CharacterBase> gettCharactersByUserId(String userId) {
        return characterRepository.findAll().stream().filter(c -> c.getCreatorId().equals(userId)).toList();
    }


}