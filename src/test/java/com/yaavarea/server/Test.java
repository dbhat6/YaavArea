package com.yaavarea.server;

import com.yaavarea.server.model.enums.RoadEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        RoadEnum road = RoadEnum.valueOf("SAFETY");
        RoadEnum roadEnum = RoadEnum.TRAFFIC;
        System.out.println(roadEnum);
        System.out.println(road);
    }
}
