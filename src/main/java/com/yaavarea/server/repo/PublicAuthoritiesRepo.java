package com.yaavarea.server.repo;

import com.yaavarea.server.model.mongo.PublicAuthorities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicAuthoritiesRepo extends MongoRepository<PublicAuthorities, String> {
    Optional<PublicAuthorities> findDistinctFirstByName(String name);
    Optional<PublicAuthorities> findById(String id);

    Page<PublicAuthorities> findAllByPartyIgnoreCase(String party, Pageable pageable);
}
