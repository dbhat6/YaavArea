package com.yaavarea.server.model;

import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class Contact {
    @Email
    private String email;

    @NotNull
    private List<Phonenumber> mobileNumber;
}
