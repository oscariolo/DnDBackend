package com.prograweb.dndbackend.domain.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Document(collection = "users")
@Getter
@Setter
public class User { 

    @Id 
    private String id; 

    @NotNull 
    @Size(min = 3, max = 50)
    private String username; 

    @NotNull
    @Email
    private String email; 

    @NotNull
    private String passwordHash; 

    private String firstName;
    private String lastName;
    
    private String profilePicture; 
    private String bio;
    private String keycloakId; 
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLogin;
    
    private boolean enabled = true;
    
    @NotNull
    private String role = "USER";
    
    public User(String username, String email, String firstName, String lastName) { 
        this.username = username; 
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
