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

public class RegionRequest {
    private int id;
    private Polygon polygonIn;
    private Polygon polygonOut;
}
