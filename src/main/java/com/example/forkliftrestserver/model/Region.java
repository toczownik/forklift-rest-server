package com.example.forkliftrestserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.awt.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Region {
    private int id;
    private Area area;
    private int forkliftSerialNumber;

    public Region(int id, Area area) {
        this.id = id;
        this.area = area;
        forkliftSerialNumber = -1;
    }

    public boolean isForkliftInside(Point point) {
        return area.doesContain(point);
    }
}
