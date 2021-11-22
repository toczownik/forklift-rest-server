package com.example.forkliftrestserver.model;

import java.awt.*;

public class Forklift {
    private int serialNumber =-1;
    private Point coords = new Point();

    public Forklift(int serialNumber, Point coords) {
        this.serialNumber = serialNumber;
        this.coords = coords;
    }

    public Forklift() {}

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Point getCoords() {
        return coords;
    }

    public void setCoords(Point coords) {
        this.coords = coords;
    }
}
