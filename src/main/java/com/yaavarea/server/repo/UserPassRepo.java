package com.yaavarea.server.repo;

import com.yaavarea.server.model.UserPass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPassRepo extends MongoRepository<UserPass, String> {
    Optional<UserPass> findByUsername(String username);
}
