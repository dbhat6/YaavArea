package com.yaavarea.server.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Getter
@Setter
public class NearbyDto {
    private GeoJsonPoint point;
    @NotNull
    private Double xCoordinate;
    @NotNull
    private Double yCoordinate;
    @NotNull
    private Double minimumDistance;
    @NotNull
    private Double maximumDistance;


    public NearbyDto(Double xCoordinate, Double yCoordinate, Double minimumDistance, Double maximumDistance) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        System.out.println(this.xCoordinate);
        System.out.println(this.yCoordinate);
        this.minimumDistance = minimumDistance;
        this.maximumDistance = maximumDistance;
        this.point = new GeoJsonPoint(this.xCoordinate, this.yCoordinate);
    }
}
