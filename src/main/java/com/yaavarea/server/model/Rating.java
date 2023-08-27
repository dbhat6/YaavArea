package com.yaavarea.server.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayDeque;

@Data
public class Rating {
    @NotNull
    private int totalScore;
    @NotNull
    private int totalRated;
    @NotNull
    private ArrayDeque<String> ratingGivenBy;

    public double fetchRating() {
        return ((double) totalScore) / totalRated;
    }

    public void addNewRating(int rating, String name) {
        int maxLength = 5;
        if (ratingGivenBy == null)
            ratingGivenBy = new ArrayDeque<>();
        if (ratingGivenBy.size() >= maxLength) {
            ratingGivenBy.pollFirst(); // Remove the oldest element
        }
        ratingGivenBy.offerLast(name); // Add the new element to the end
        totalScore += rating;
        totalRated++;
    }
}
