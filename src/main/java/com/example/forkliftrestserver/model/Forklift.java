package com.example.forkliftrestserver.model;

import lombok.*;
import java.awt.*;

import javax.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Forklift {
    private String serialNumber = "";
    private Point coords = new Point();
    private ForkliftState state = ForkliftState.ACTIVE;
    private Date lastConnection;

    public Forklift(String serialNumber, Point coords) {
        this.serialNumber = serialNumber;
        this.coords = coords;
    }

    public void updateLastConnection() {
        lastConnection = new Date();
    }
}
