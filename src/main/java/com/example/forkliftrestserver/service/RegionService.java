package com.example.forkliftrestserver.service;

import com.example.forkliftrestserver.model.Region;
import org.springframework.stereotype.Service;

import java.awt.*;

//@Service
public class RegionService {
    private Region region;

    public boolean isForkliftInside(Point point) {
        return region.getPolygon().contains(point);
    }

    public boolean isTheSame(Region regionFromClient) {
        if (region.getId() != regionFromClient.getId()) return false;

        for (int i = 0; i < region.getPolygon().xpoints.length; i++) {
            if (region.getPolygon().xpoints[i] != regionFromClient.getPolygon().xpoints[i]) {
                return false;
            }
            if (region.getPolygon().ypoints[i] != regionFromClient.getPolygon().ypoints[i]) {
                return false;
            }
        }
        return true;
    }
}
