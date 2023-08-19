package com.yaavarea.server.repo;

import com.yaavarea.server.model.EmailAddress;
import com.yaavarea.server.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
//    User findByEmail(EmailAddress email);
}
