package com.example.forkliftrestserver.model;

import java.awt.*;

public class Region {
    private int id;
    // TODO: Polygon nie działa poprawnie, potrzebna nowa klasa
    private Polygon quad;
    private int forkliftSerialNumber;

    public Region(int id, Polygon quad) {
        this.id = id;
        this.quad = quad;
        forkliftSerialNumber = -1;
    }

    public Region() {
    }

    public boolean isForkliftInside(Point point) {
        return quad.contains(point);
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

    public Polygon getQuad() {
        return quad;
    }

    public void setQuad(Polygon quad) {
        this.quad = quad;
    }
}
