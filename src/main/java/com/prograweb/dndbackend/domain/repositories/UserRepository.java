package com.prograweb.dndbackend.repositories;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.prograweb.dndbackend.models.User;

public interface UserRepository extends MongoRepository<User,String>{
    
    public Optional<User> findById(String id);
    
}
