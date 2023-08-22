package com.yaavarea.server.model.dto;

import com.yaavarea.server.model.MapObject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.time.LocalDate;

@Data
@Builder
public class EventDto extends MapObject {
    @NotNull
    @NotBlank
    private String type;
    @NotNull
    private LocalDate expireAt;
    @NotNull
    private GeoJsonPoint geometry;
}
