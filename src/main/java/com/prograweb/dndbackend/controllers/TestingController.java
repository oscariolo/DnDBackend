package com.prograweb.dndbackend.controllers;
import org.springframework.web.bind.annotation.RestController;

import com.prograweb.dndbackend.domain.models.User;
import com.prograweb.dndbackend.domain.models.characters.EnemyCharacter;
import com.prograweb.dndbackend.domain.models.characters.PlayableCharacter;
import com.prograweb.dndbackend.services.CharacterService;
import com.prograweb.dndbackend.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@RestController
@RequestMapping("/api/testing")
public class TestingController {

    @Autowired
    private UserService userService;

    @Autowired
    private CharacterService characterService;

    
    @GetMapping("/healthcheck")
    public String getMethodName() {
        return new String("Service is up and running!");
    }

    @PostMapping("/testUser")
    public ResponseEntity<List<User>> testUser() {
        userService.addUser(new User("mockUser", "mockEmail@example.com"));
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/testPlayableCharacter")
    public ResponseEntity<List<PlayableCharacter>> testPlayableCharacter() {
        PlayableCharacter character = new PlayableCharacter("mockId", "mockCreatorId", "mockName", Map.of(), "mockClass", List.of(), List.of());
        EnemyCharacter enemy = new EnemyCharacter("enemyId", "enemyCreatorId", "enemyName", "mockEnemyType", 1);
        characterService.addCharacter(character);
        characterService.addCharacter(enemy);
        return ResponseEntity.ok(characterService.getAllPlayableCharacters());
    }
    
    
    

}
