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

    public void addCharacter(CharacterBase character) {
        characterRepository.save(character);
    }

    public List<PlayableCharacter> getAllPlayableCharacters() {
        return characterRepository.findAllPlayableCharacters();
    }


}