package com.yaavarea.server.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class CommonDaoImpl {
    private MongoTemplate mongoTemplate;

    @Autowired
    public CommonDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Object> findByGeometryNearby(GeoJsonPoint point, double min, double max, Class entityClass) {
        System.out.println(point);
        System.out.println(min);
        System.out.println(max);
        System.out.println(entityClass);
        NearQuery query = NearQuery.near(point).minDistance(new Distance(min, Metrics.KILOMETERS))
                .maxDistance(new Distance(max, Metrics.KILOMETERS));
        GeoResults results = mongoTemplate.geoNear(query, entityClass);
        System.out.println(results);
        List<Object> all = new ArrayList<>();
        Iterator iter = results.iterator();
        while (iter.hasNext()) {
            GeoResult temp = (GeoResult) iter.next();
            all.add(temp.getContent());
        }

        return all;
    }
}
