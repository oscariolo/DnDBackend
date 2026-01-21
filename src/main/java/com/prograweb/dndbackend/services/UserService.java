package com.prograweb.dndbackend.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prograweb.dndbackend.domain.dtos.RegisterUserDTO;
import com.prograweb.dndbackend.domain.models.User;
import com.prograweb.dndbackend.domain.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeycloakService keycloakService;

    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmailOrUsername(String emailOrUsername) {
        var userByEmail = userRepository.findByEmail(emailOrUsername);
        if (userByEmail.isPresent()) {
            return userByEmail.get();
        }
        var userByUsername = userRepository.findByUsername(emailOrUsername);
        return userByUsername.orElse(null);
    }

    public User registerUser(RegisterUserDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        String keycloakId = keycloakService.createUser(
                registerDTO.getUsername(),
                registerDTO.getEmail(),
                registerDTO.getFirstName(),
                registerDTO.getLastName(),
                registerDTO.getPassword()
        );

        User user = new User(
                registerDTO.getUsername(),
                registerDTO.getEmail(),
                registerDTO.getFirstName(),
                registerDTO.getLastName()
        );
        user.setKeycloakId(keycloakId);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole("USER");
        user.setEnabled(true);

        return userRepository.save(user);
    }
}

