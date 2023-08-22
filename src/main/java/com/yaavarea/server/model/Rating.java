package com.yaavarea.server.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayDeque;

@Data
public class Rating {
    @NotNull
    private int totalScore;
    @NotNull
    private int totalRated;
    @Setter(AccessLevel.NONE)
    @NotNull
    private ArrayDeque<String> ratingGivenBy;

    public double fetchRating() {
        return ((double) totalScore) / totalRated;
    }

    public void addRater(String value) {
        int maxLength = 5;
        if (ratingGivenBy.size() >= maxLength) {
            ratingGivenBy.pollFirst(); // Remove the oldest element
        }
        ratingGivenBy.offerLast(value); // Add the new element to the end
    }
}
