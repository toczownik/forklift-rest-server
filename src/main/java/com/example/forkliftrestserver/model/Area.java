package com.example.forkliftrestserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.awt.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    Point[] points;

    public boolean doesContain(Point point) {
        return isBetweenPoints(point, points[0], points[2]) && isBetweenPoints(point, points[1], points[3]);
    }

    // TODO: uwględnienie figur wklęsłych
    private boolean isBetweenPoints(Point point, Point a, Point b) {
        if (a.x >= b.x) {
            if (point.x > a.x || point.x < b.x) {
                return false;
            }
        } else if (point.x < a.x || point.x > b.x) {
            return false;
        }
        if (a.y >= b.y) {
            return point.y <= a.y && point.y >= b.y;
        } else return point.y >= a.y && point.y <= b.y;
    }
}
