package com.yaavarea.server.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class MapObject {

    private String id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String description;
    private Rating rating;


}
