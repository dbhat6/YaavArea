package com.yaavarea.server.repo;

import com.yaavarea.server.model.mongo.Constituencies;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConstituencyRepo extends MongoRepository<Constituencies, String> {
    Optional<Constituencies> findByName(String name);
}
