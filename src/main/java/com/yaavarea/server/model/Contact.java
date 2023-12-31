package com.yaavarea.server.model;

import com.google.i18n.phonenumbers.Phonenumber;
import com.yaavarea.server.config.RequiredKeys;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
public class Contact {
    private Set<String> email;

    @NotNull
    @RequiredKeys({"name", "countryCode", "contactNumber"})
    private List<Map<String, String>> mobileNumbers;

    public Contact() {
        this.email = new HashSet<>();
        this.mobileNumbers = new ArrayList<>();
    }
}
