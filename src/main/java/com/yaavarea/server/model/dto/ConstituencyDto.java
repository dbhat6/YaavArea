package com.yaavarea.server.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.wololo.geojson.GeoJSON;

@Data
@Builder
public class ConstituencyDto {
    private String id;
    @NotNull
    @NotBlank
    private String name;
    private String leader;
    private String fact;
    @NotNull
    private GeoJsonPolygon geometry;
}
