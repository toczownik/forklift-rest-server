package com.example.forkliftrestserver.model;

import lombok.*;

import java.awt.*;
import javax.persistence.Entity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Region {
    private int id;
    private Polygon polygon;
    private String forkliftSerialNumber;

    public Region(int id, Polygon polygon) {
        this.id = id;
        forkliftSerialNumber = "";
        this.polygon = polygon;
    }

    public boolean isForkliftInside(Point point) {
        return polygon.contains(point);
    }

    public boolean isTheSame(Region regionFromClient) {
        if (this.id != regionFromClient.id) return false;

        for (int i = 0; i < this.getPolygon().xpoints.length; i++) {
            if (this.getPolygon().xpoints[i] != regionFromClient.getPolygon().xpoints[i]) {
                return false;
            }
            if (this.getPolygon().ypoints[i] != regionFromClient.getPolygon().ypoints[i]) {
                return false;
            }
        }
        return true;
    }
}
