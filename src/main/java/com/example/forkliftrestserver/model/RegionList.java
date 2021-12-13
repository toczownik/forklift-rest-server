package com.example.forkliftrestserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
                if (region.getForkliftSerialNumber().equals("") || region.getForkliftSerialNumber().equals(forklift.getSerialNumber())) {
                    if (region.isTheSame(regionFromClient)) {
                        region.setForkliftSerialNumber(forklift.getSerialNumber());
                        return new PermissionMessage(RegionState.SUCCESS, region.getForkliftSerialNumber());
                    }
                } else {
                    forklift.setState(ForkliftState.WAITING);
                    return new PermissionMessage(RegionState.OCCUPIED, region.getForkliftSerialNumber());
                }
            }
        }
        return new PermissionMessage(RegionState.LACK, "");
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

    public boolean anyTakenByForklift(Forklift forklift) {
        for (Region region : regions) {
            if (region.getForkliftSerialNumber().equals(forklift.getSerialNumber())) {
                return true;
            }
        }
        return false;
    }

//    do przeanalizowania i przetestowania !
    public ResponseEntity<Forklift> leaveRegionByForklift(Forklift forklift, Region regionToLeave) {
        for (Region region : regions) {
            if (region.isTheSame(regionToLeave)) {
                if (!region.isForkliftInside(forklift.getCoords()) && region.getForkliftSerialNumber().equals(forklift.getSerialNumber())) {
                    region.setForkliftSerialNumber("");
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity <>(HttpStatus.NOT_FOUND);
                }
            }
        }
        return new ResponseEntity <>(HttpStatus.NOT_FOUND);
    }
}
