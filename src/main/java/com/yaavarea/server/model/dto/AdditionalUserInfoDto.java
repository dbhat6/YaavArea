package com.yaavarea.server.model.dto;

import com.yaavarea.server.model.enums.XpCategoriesEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
public class AdditionalUserInfoDto {
    private int xp;
    private int level;
    private double distanceWalked;
    private int cardsRated;
    private int cardsVisited;

    public AdditionalUserInfoDto() {
        this.setLevel(1);
    }

    public void addRate() {
        cardsRated++;
    }

    public void addVisit() {
        cardsVisited++;
    }

    public void gainXp(XpCategoriesEnum xpEarned) {
        if((xp + xpEarned.getXp()) > level * 1.5 * 100) {
            xp = (int) ((xp + xpEarned.getXp()) % (level * 1.5 * 100));
            level++;
        } else {
            xp = xp + xpEarned.getXp();
        }
    }

}
