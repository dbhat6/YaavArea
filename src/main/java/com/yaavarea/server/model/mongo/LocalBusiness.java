package com.yaavarea.server.model.mongo;

import com.yaavarea.server.model.Rating;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class LocalBusiness {
    @Id
    private String id;
    private String name;
    private String description;
    private Rating rating;
    private String type;

    @GeoSpatialIndexed(useGeneratedName = true, type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonMultiPoint geometry;

    public void addNewPoint(GeoJsonPoint point) {
        if (this.geometry == null)
            this.geometry = new GeoJsonMultiPoint(point);
        else
            this.geometry.getCoordinates().add(point);
    }
}
