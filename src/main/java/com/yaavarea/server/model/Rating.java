package com.yaavarea.server.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

@Data
public class Rating {
    @NotNull
    private int totalScore;
    @NotNull
    private int totalRated;
    @NotNull
    private Map<String, Integer> ratings;

    public double fetchRating() {
        return ((double) totalScore) / totalRated;
    }

    public boolean addNewRating(int rating, String id) {
        if(ratings == null)
            ratings = new HashMap<>();

        Integer rate = ratings.get(id);
        ratings.put(id, rating);
        totalScore += rating;

        if (rate != null) {
            totalScore -= rate;
            return false;
        }
        else {
            totalRated++;
            return true;
        }
    }
}
