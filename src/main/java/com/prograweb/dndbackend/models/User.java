package com.prograweb.dndbackend.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "users")
@Getter
@Setter
public class User {

    @Id
    private String id;

    private String username;

    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
    
}
