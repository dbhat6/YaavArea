package com.yaavarea.server.model.mongo;

import com.yaavarea.server.model.Rating;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
public class Events {
    @Id
    private String id;
    private String name;
    private String description;
    private Rating rating;
    private String type;

    @Indexed(expireAfterSeconds = 0)
    private LocalDate expireAt;

    @GeoSpatialIndexed(useGeneratedName = true, type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint geometry;
}
