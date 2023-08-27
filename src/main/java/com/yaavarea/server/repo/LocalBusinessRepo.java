package com.yaavarea.server.repo;

import com.yaavarea.server.model.mongo.Events;
import com.yaavarea.server.model.mongo.LocalBusiness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalBusinessRepo extends MongoRepository<LocalBusiness, String> {
    Optional<LocalBusiness> findDistinctFirstByName(String name);

    Page<LocalBusiness> findAllByTypeIgnoreCase(String type, Pageable pageable);
}
