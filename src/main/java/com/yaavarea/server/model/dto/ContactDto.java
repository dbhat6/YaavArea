package com.yaavarea.server.model.dto;

import com.yaavarea.server.config.RequiredKeys;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
public class ContactDto {
    private Set<String> email;

    @NotNull
    @RequiredKeys({"name", "countryCode", "contactNumber"})
    private List<Map<String, String>> mobileNumbers;

    public ContactDto() {
        this.email = new HashSet<>();
        this.mobileNumbers = new ArrayList<>();
    }
}
