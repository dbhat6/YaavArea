package com.yaavarea.server.repo;

import com.yaavarea.server.model.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndFirstNameAndLastName(String email, String firstName, String lastName);

    void deleteByEmail(String email);

    boolean existsById(String id);
}
