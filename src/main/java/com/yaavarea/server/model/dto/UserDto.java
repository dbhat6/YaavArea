package com.yaavarea.server.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class UserDto {
    @NotNull
    @NotBlank
    private String id;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @Email
    @NotNull
    private String email;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    private Date dateOfBirth;
    private String[] roles;
    private int xp;
    private int xpLevel;
}
