package com.prograweb.dndbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prograweb.dndbackend.domain.models.characters.PlayableCharacter;
import com.prograweb.dndbackend.services.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/characters")
public class CharacterController {
    
    @Autowired
    private CharacterService characterService;

    @GetMapping("/playable")
    public ResponseEntity<List<PlayableCharacter>> getPlayableCharacters() {
        return ResponseEntity.ok(characterService.getAllPlayableCharacters());
    }
}