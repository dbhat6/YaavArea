package com.yaavarea.server.model.dto;

import com.yaavarea.server.model.Contact;
import com.yaavarea.server.model.MapObject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Data
@Builder
public class PublicAuthorityDto extends MapObject {
    @NotNull
    @NotBlank
    private String party;
    @NotNull
    private ContactDto contact;

    @NotNull
    private GeoJsonPoint geometry;
}
