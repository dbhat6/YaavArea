package com.yaavarea.server.model.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private int xpLevel;
    private int xp;

    @Indexed(unique = true)
    private String email;

    public void gainXp(int xpEarned) {
        if((xp + xpEarned) > xpLevel * 1.5 * 100) {
            xpLevel++;
            xp = (int) ((xp + xpEarned) % xpLevel * 1.5 * 100);
        } else {
            xp = xp + xpEarned;
        }
    }

    public User update(final String id, final User user) {
        final User newUser =  new User();
        newUser.setId(id);
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setDateOfBirth(user.getDateOfBirth());
        newUser.setXp(user.getXp());
        newUser.setXpLevel(user.getXpLevel());
        return newUser;
    }
}
