package com.yaavarea.server.model.dto;

import com.yaavarea.server.config.RequiredKeys;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @NotNull
    @RequiredKeys({"name", "countryCode", "contactNumber"})
    @Size(max = 1)
    private List<Map<String, String>> mobileNumber;

    private AdditionalUserInfoDto userStats;

    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
}
