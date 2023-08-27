package com.yaavarea.server.repo;

import com.yaavarea.server.model.mongo.Events;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventsRepo extends MongoRepository<Events, String> {
    Optional<Events> findDistinctFirstByName(String name);
}
