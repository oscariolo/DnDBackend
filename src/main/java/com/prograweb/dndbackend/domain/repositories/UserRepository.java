package com.prograweb.dndbackend.domain.repositories;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.prograweb.dndbackend.domain.models.User;

public interface UserRepository extends MongoRepository<User,String>{
    
    public Optional<User> findById(String id);
    
}
