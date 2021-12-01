package com.example.forkliftrestserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RegionList {
    private List<Region> regions;

    synchronized public PermissionMessage isForkliftMovementAllowed(Forklift forklift, Region regionFromClient) {
        for (Region region : regions) {
            if (region.isForkliftInside(forklift.getCoords())) {
                if (region.getForkliftSerialNumber().equals("")){
                    if(region.isTheSame(regionFromClient)){
                        region.setForkliftSerialNumber(forklift.getSerialNumber());
                        return new PermissionMessage(RegionState.SUCCESS, region.getForkliftSerialNumber());
                    }
                } else {
                    forklift.setState(ForkliftState.WAITING);
                    return new PermissionMessage(RegionState.OCCUPIED, region.getForkliftSerialNumber());
                }
            }
        }
        return new PermissionMessage(RegionState.OCCUPIED, "");
    }

    public void freeAssignedRegions(String serialNumber) {
        for (Region region : regions) {
            if (region.getForkliftSerialNumber().equals(serialNumber)) {
                region.setForkliftSerialNumber("");
            }
        }
    }

    public void addRegion(Region region) {
        regions.add(region);
    }
}
