package com.yaavarea.server.utils;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GeoUtils {
    public static GeoJsonMultiPoint addNewPoint(GeoJsonMultiPoint geoJsonMultiPoint, GeoJsonPoint geoJsonPoint) {
        Point point = new Point(geoJsonPoint.getX(), geoJsonPoint.getY());
        List<Point> pointList = geoJsonMultiPoint.getCoordinates();
        List<Point> newPointList =
                pointList.stream()
                        .limit(pointList.size() - 1)
                        .collect(Collectors.toCollection(() -> new ArrayList<>(pointList.size())));
        newPointList.add(pointList.get(pointList.size() - 1));
        newPointList.add(point);
        return new GeoJsonMultiPoint(newPointList);
    }
}
