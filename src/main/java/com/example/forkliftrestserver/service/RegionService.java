package com.example.forkliftrestserver.service;

import com.example.forkliftrestserver.model.Region;

import java.awt.*;

//@Service
public class RegionService {
    private Region region;

    public boolean isForkliftInside(Point point) {
        return region.getPolygonIn().contains(point);
    }

    public boolean isTheSame(Region regionFromClient) {
        if (region.getId() != regionFromClient.getId()) return false;

        for (int i = 0; i < region.getPolygonIn().xpoints.length; i++) {
            if (region.getPolygonIn().xpoints[i] != regionFromClient.getPolygonIn().xpoints[i]) {
                return false;
            }
            if (region.getPolygonIn().ypoints[i] != regionFromClient.getPolygonIn().ypoints[i]) {
                return false;
            }
        }
        return true;
    }
}
