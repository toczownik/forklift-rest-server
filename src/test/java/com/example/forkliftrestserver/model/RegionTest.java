package com.example.forkliftrestserver.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class RegionTest {

    private Region region =  new Region();

    @BeforeEach
    void init(){
        int[] xpoints = {200, 200, 210, 210};
        int[] ypoints = {200, 210, 210, 200};
        region.setId(0);
        region.setPolygonIn(new Polygon(xpoints, ypoints, 4));
        region.setPolygonOut(new Polygon(xpoints, ypoints, 4));
    }
    @Test
    void isForkliftInsideTrue() {
        boolean response = region.isForkliftInside(new Point(205, 205));

        assertTrue(response);
    }

    @Test
    void isForkliftInsideFalse() {
        boolean response = region.isForkliftInside(new Point(305, 305));

        assertFalse(response);
    }

    @Test
    void isTheSameTrue() {
        RegionRequest secondRegion = new RegionRequest();
        int[] xpoints = {200, 200, 210, 210};
        int[] ypoints = {200, 210, 210, 200};
        secondRegion.setId(0);
        secondRegion.setPolygonIn(new Polygon(xpoints, ypoints, 4));
        secondRegion.setPolygonOut(new Polygon(xpoints, ypoints, 4));

        boolean response = region.isTheSame(secondRegion);

        assertTrue(response);
    }

    @Test
    void isTheSameDifferentPolygonReturnFalse() {
        RegionRequest secondRegion = new RegionRequest();
        int[] xpoints = {50, 50, 55, 55};
        int[] ypoints = {50, 55, 55, 50};
        secondRegion.setId(0);
        secondRegion.setPolygonIn(new Polygon(xpoints, ypoints, 4));
        secondRegion.setPolygonOut(new Polygon(xpoints, ypoints, 4));

        boolean response = region.isTheSame(secondRegion);

        assertFalse(response);
    }

    @Test
    void isTheSameDifferentIdReturnFalse() {
        RegionRequest secondRegion = new RegionRequest();
        int[] xpoints = {200, 200, 210, 210};
        int[] ypoints = {200, 210, 210, 200};
        secondRegion.setId(12);
        secondRegion.setPolygonIn(new Polygon(xpoints, ypoints, 4));
        secondRegion.setPolygonOut(new Polygon(xpoints, ypoints, 4));

        boolean response = region.isTheSame(secondRegion);

        assertFalse(response);
    }
}