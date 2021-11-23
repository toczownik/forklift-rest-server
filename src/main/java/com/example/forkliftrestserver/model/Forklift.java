package com.example.forkliftrestserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.*;
import java.util.Date;

public class Forklift {
    private int serialNumber = -1;
    private Point coords = new Point();
    private ForkliftState state = ForkliftState.ACTIVE;
    @JsonIgnore
    private Date lastConnection = new Date();

    public Forklift(int serialNumber, Point coords) {
        this.serialNumber = serialNumber;
        this.coords = coords;
    }

    public Date getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(Date lastConnection) {
        this.lastConnection = lastConnection;
    }

    public void updateLastConnection() {
        lastConnection = new Date();
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

    public ForkliftState getState() {
        return state;
    }

    public void setState(ForkliftState state) {
        this.state = state;
    }
}
