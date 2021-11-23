package com.example.forkliftrestserver.model;

import java.awt.*;

public class Region {
    private int id;
    private Area area;
    private int forkliftSerialNumber;

    public Region(int id, Area area) {
        this.id = id;
        this.area = area;
        forkliftSerialNumber = -1;
    }

    public Region() {
    }

    public boolean isForkliftInside(Point point) {
        return area.doesContain(point);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getForkliftSerialNumber() {
        return forkliftSerialNumber;
    }

    public void setForkliftSerialNumber(int forkliftSerialNumber) {
        this.forkliftSerialNumber = forkliftSerialNumber;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
