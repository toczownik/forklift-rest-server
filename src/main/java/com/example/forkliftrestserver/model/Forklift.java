package com.example.forkliftrestserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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

    public void updateLastConnection() {
        lastConnection = new Date();
    }
}
