package com.yaavarea.server.repo;

import com.yaavarea.server.model.mongo.Otp;
import com.yaavarea.server.model.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepo extends MongoRepository<Otp, String> {
    Optional<Otp> findByPhoneNumber(String phoneNumber);
}
