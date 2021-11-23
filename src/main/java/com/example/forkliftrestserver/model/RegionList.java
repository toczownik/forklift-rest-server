package com.example.forkliftrestserver.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionList {
    private List<Region> regions;

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public boolean isForkliftMovementAllowed(Forklift forklift) {
        Map<Integer, Integer> updates = new HashMap<>();
        int semaphoreIndex = 0;
        for (Region region : regions) {
            if (region.isForkliftInside(forklift.getCoords())) {
                if (region.getForkliftSerialNumber() == -1) {
                    updates.put(semaphoreIndex, forklift.getSerialNumber());
                } else if (!(region.getForkliftSerialNumber() == forklift.getSerialNumber())) {
                    return false;
                }
            } else if (region.getForkliftSerialNumber() == forklift.getSerialNumber()) {
                updates.put(semaphoreIndex, null);
            }
            semaphoreIndex++;
        }
        for (Integer semaphoreId:
             updates.keySet()) {
            if (updates.get(semaphoreId) == null) {
                regions.get(semaphoreId).setForkliftSerialNumber(-1);
            } else {
                regions.get(semaphoreId).setForkliftSerialNumber(forklift.getSerialNumber());
            }
        }
        return true;
    }

    public RegionList() {
    }

    public RegionList(List<Region> regions) {
        this.regions = regions;
    }

    public void addSemaphore(Region region) {
        regions.add(region);
    }
}
