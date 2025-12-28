package com.prograweb.dndbackend.domain.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "users")
@Getter
@Setter
public class User {

    @Id
    private String id;

    @NotNull
    private final String username;

    @Email
    private final String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
    
}
