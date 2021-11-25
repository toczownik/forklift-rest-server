package com.example.forkliftrestserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RegionList {
    private List<Region> regions;

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

    public void freeAssignedRegions(int serialNumber) {
        for (Region region: regions) {
            if (region.getForkliftSerialNumber() == serialNumber) {
                region.setForkliftSerialNumber(-1);
            }
        }
    }

    public void addRegion(Region region) {
        regions.add(region);
    }
}
