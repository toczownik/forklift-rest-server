package com.example.forkliftrestserver.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Forklift {
    private int serialNumber = -1;
    private Point coords = new Point();
    private ForkliftState state = ForkliftState.ACTIVE;
    private Date lastConnection = new Date();
    private List<Integer> takenRegionsList = new ArrayList<>();

    public Forklift(int serialNumber, Point coords) {
        this.serialNumber = serialNumber;
        this.coords = coords;
    }

    public void addRegion(int regionId) {
        if (!takenRegionsList.contains(regionId)) {
            takenRegionsList.add(regionId);
        }
    }

    public void removeRegion(int regionId) {
        if (takenRegionsList.contains(regionId)) {
            takenRegionsList.remove(regionId);
        }
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

    public List<Integer> getTakenRegionsList() {
        return takenRegionsList;
    }

    public void setTakenRegionsList(List<Integer> takenRegionsList) {
        this.takenRegionsList = takenRegionsList;
    }
}
