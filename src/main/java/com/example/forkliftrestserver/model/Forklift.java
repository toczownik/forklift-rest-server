package com.example.forkliftrestserver.model;

import lombok.*;

import javax.persistence.Entity;
import java.awt.geom.Point2D;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Forklift {
    private String serialNumber = "";
    private Point2D.Double coords = new Point2D.Double();
    private ForkliftState state = ForkliftState.ACTIVE;
    private Date lastConnection;

    public Forklift(String serialNumber, Point2D.Double coords) {
        this.serialNumber = serialNumber;
        this.coords = coords;
    }
}
