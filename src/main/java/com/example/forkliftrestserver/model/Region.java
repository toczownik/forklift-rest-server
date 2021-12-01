package com.example.forkliftrestserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private int forkliftSerialNumber;

    public Region(int id, Polygon polygon) {
        this.id = id;
        //this.area = area;
        forkliftSerialNumber = -1;
        this.polygon = polygon;
    }

    public boolean isForkliftInside(Point point) {
        return polygon.contains(point);
    }
}
