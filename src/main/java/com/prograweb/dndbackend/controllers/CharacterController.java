package com.prograweb.dndbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prograweb.dndbackend.domain.models.characters.CharacterBase;
import com.prograweb.dndbackend.domain.models.characters.PlayableCharacter;
import com.prograweb.dndbackend.services.CharacterService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/characters")
public class CharacterController {
    
    @Autowired
    private CharacterService characterService;

    @GetMapping()
    public ResponseEntity<List<CharacterBase>> getAllCharacters() {
        return ResponseEntity.ok(characterService.getAllCharacters());
    }
    

    @GetMapping("/playable")
    public ResponseEntity<List<PlayableCharacter>> getPlayableCharacters() {
        return ResponseEntity.ok(characterService.getAllPlayableCharacters());
    }

    @PostMapping()
    public ResponseEntity<?> postCharacter(@Valid @RequestBody CharacterBase entity) {
        try{
            characterService.addCharacter(entity);
            return ResponseEntity.ok(entity);
        }catch(HttpMessageNotReadableException e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Invalid character type or data");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }   
    }
    

}