package com.prograweb.dndbackend.domain.models.characters;

import org.springframework.data.annotation.PersistenceCreator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EnemyCharacter extends CharacterBase {
    private String enemyType;
    private int challengeRating;

    @PersistenceCreator
    public EnemyCharacter(String id, String creatorId, String name, String enemyType, int challengeRating) {
        super(id, creatorId, name);
        this.enemyType = enemyType;
        this.challengeRating = challengeRating;
    }

}
