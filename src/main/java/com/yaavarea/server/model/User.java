package com.yaavarea.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private int xpLevel;
    private int xp;

    @Indexed(unique = true)
    private String email;

//    public User(String id,
//                String firstName,
//                String lastName,
//                Date dateOfBirth,
//                int xpLevel,
//                int xp,
//                String email) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.dateOfBirth = dateOfBirth;
//        this.xp = xp;
//        this.xpLevel = xpLevel;
//        this.email = new EmailAddress(email);
//    }

//    public void setEmail(String email) {
//        EmailAddress emailAddress = new EmailAddress(email);
//        this.email = emailAddress;
//    }
}
