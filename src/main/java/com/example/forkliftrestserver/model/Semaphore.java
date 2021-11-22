package com.example.forkliftrestserver.model;

import java.awt.*;

public class Semaphore {
    private int id;
    private Polygon quad;
    private Forklift forklift;

    public Semaphore(int id, Polygon quad) {
        this.id = id;
        this.quad = quad;
        forklift = null;
    }

    public Semaphore() {}

    public boolean isForkliftInside(Point point) {
        return quad.contains(point);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Forklift getForklift() {
        return forklift;
    }

    public void setForklift(Forklift forklift) {
        this.forklift = forklift;
    }

    public Polygon getQuad() {
        return quad;
    }

    public void setQuad(Polygon quad) {
        this.quad = quad;
    }
}
