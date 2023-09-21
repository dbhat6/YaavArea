package com.yaavarea.server.model.mongo;

import com.yaavarea.server.model.dto.AdditionalUserInfoDto;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document
@Data
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    @Indexed(unique = true)
    private String email;

    private List<Map<String, String>> mobileNumber;

    private AdditionalUserInfoDto userStats;

    @Indexed
    @CreatedDate
    private LocalDateTime createdAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @LastModifiedBy
    private String updatedBy;

//    public User update(final String id, final User user) {
//        final User newUser =  new User();
//        newUser.setId(id);
//        newUser.setEmail(user.getEmail());
//        newUser.setFirstName(user.getFirstName());
//        newUser.setLastName(user.getLastName());
//        newUser.setDateOfBirth(user.getDateOfBirth());
//        newUser.setXp(user.getXp());
//        newUser.setXpLevel(user.getXpLevel());
//        return newUser;
//    }
}
