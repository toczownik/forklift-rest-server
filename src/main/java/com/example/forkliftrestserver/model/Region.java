package com.example.forkliftrestserver.model;

import lombok.*;

import java.awt.*;
import java.awt.geom.Point2D;
import javax.persistence.Entity;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Region {
    private int id;
    private Polygon polygonIn;
    private Polygon polygonOut;
    private String forkliftSerialNumber;

    public Region(int id, Polygon polygonIn, Polygon polygonOut) {
        this.id = id;
        forkliftSerialNumber = "";
        this.polygonIn = polygonIn;
        this.polygonOut = polygonOut;
    }

    public boolean isForkliftInside(Point2D point) {
        return polygonIn.contains(point);
    }

    public boolean isTheSame(RegionRequest regionFromClient) {
        if (this.id != regionFromClient.getId()) return false;

        for (int i = 0; i < this.getPolygonIn().xpoints.length; i++) {
            if (this.getPolygonIn().xpoints[i] != regionFromClient.getPolygonIn().xpoints[i]) {
                return false;
            }
            if (this.getPolygonIn().ypoints[i] != regionFromClient.getPolygonIn().ypoints[i]) {
                return false;
            }
            if (this.getPolygonOut().xpoints[i] != regionFromClient.getPolygonOut().xpoints[i]) {
                return false;
            }
            if (this.getPolygonOut().ypoints[i] != regionFromClient.getPolygonOut().ypoints[i]) {
                return false;
            }
        }
        return true;
    }
}
