package com.yaavarea.server.service;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.geojson.Geometry;
import com.mongodb.client.model.geojson.Point;
import com.yaavarea.server.config.MongoConfig;
import com.yaavarea.server.model.dto.ConstituencyDto;
import com.yaavarea.server.model.mapper.MapStructMapperImpl;
import com.yaavarea.server.model.mongo.Constituencies;
import com.yaavarea.server.repo.ConstituencyRepo;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoCollectionUtils;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;
import org.wololo.geojson.GeoJSON;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class ConstituencyService {

    private ConstituencyRepo constituencyRepo;
    private MapStructMapperImpl mapper;

    private MongoConfig mongoConfig;
    private MongoClient mongoClient;
    private MongoCollection<Document> collection;

    @Autowired
    public ConstituencyService(ConstituencyRepo constituencyRepo, MapStructMapperImpl mapper, MongoConfig mongoConfig) {
        this.constituencyRepo = constituencyRepo;
        this.mapper = mapper;
        this.mongoConfig = mongoConfig;
        this.mongoClient = mongoConfig.mongoClient();
        this.collection = mongoClient.getDatabase("YaavArea").getCollection("constituencies");
    }

    public Constituencies createConstituency(ConstituencyDto constituencyDto) {
        Constituencies constituencies = mapper.constituenciesDtoToConstituencies(constituencyDto);
        return constituencyRepo.insert(constituencies);
    }

    public Constituencies getConstituencyByName(String name) {
        Optional<Constituencies> constituency = constituencyRepo.findByName(name);
        if(constituency.isPresent())
            return constituency.get();
        else
            throw new NoSuchElementException("No user found!");
    }

    public long countConstituencies() {
        return constituencyRepo.count();
    }

    public void checkIntersection(Point point) {
        Geometry geom = point;
        FindIterable<Document> asd = collection.find(
                Filters.geoIntersects("geometry", geom));
        System.out.println(asd.first());
    }
}
