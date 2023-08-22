package com.yaavarea.server.model.mongo;

import com.yaavarea.server.model.Contact;
import com.yaavarea.server.model.Rating;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class PublicAuthorities {
    @Id
    private String id;
    private String name;
    private String description;
    private Rating rating;
    private String party;
    private Contact contact;

    @GeoSpatialIndexed(useGeneratedName = true, type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonMultiPoint geometry;
}
