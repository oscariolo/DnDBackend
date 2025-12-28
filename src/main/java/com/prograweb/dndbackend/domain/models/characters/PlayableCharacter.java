package com.prograweb.dndbackend.domain.models.characters;

import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.TypeAlias;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@TypeAlias("PlayableCharacter")
public class PlayableCharacter extends CharacterBase {

    private final Map<String, Integer> attributes;
    private final String characterClass;
    private List<String> skills;
    private List<String> inventoryItems;

    @PersistenceCreator
    public PlayableCharacter(String id, String creatorId, String name, Map<String, Integer> attributes,
            String characterClass, List<String> skills, List<String> inventoryItems) {
        super(id, creatorId, name);
        this.attributes = attributes;
        this.characterClass = characterClass;
        this.skills = skills == null ? List.of() : skills;
        this.inventoryItems = inventoryItems == null ? List.of() : inventoryItems;
    }

    public void levelUpAttribute(String attribute, int increment) {
        if (attributes.containsKey(attribute)) {
            attributes.put(attribute, attributes.get(attribute) + increment);
        }
    }
    
}
