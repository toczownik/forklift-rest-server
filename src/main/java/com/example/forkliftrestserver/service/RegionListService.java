package com.example.forkliftrestserver.service;

import com.example.forkliftrestserver.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.net.URL;
import java.util.List;

@Service
public class RegionListService {
    private RegionList regionList;

    public RegionListService() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource("static/config.yml");
        Constructor regionListConstructor = new Constructor(RegionList.class);
        TypeDescription regionDescription = new TypeDescription(Region.class);
        regionDescription.addPropertyParameters("regions", Region.class, Object.class);
        regionListConstructor.addTypeDescription(regionDescription);
        Yaml yaml = new Yaml();
        try {
            regionList = yaml.load(resource.openStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized public PermissionMessage getPermission(ForkliftRequest forklift, RegionRequest regionFromClient) {
        for (Region region : regionList.getRegions()) {
            if (region.isForkliftInside(forklift.getCoords())) {
                if (region.isTheSame(regionFromClient)) {
                    if (region.getForkliftSerialNumber().equals("") || region.getForkliftSerialNumber().equals(forklift.getSerialNumber())) {
                        region.setForkliftSerialNumber(forklift.getSerialNumber());
                        return new PermissionMessage(RegionState.SUCCESS, region.getForkliftSerialNumber());
                    } else {
                        return new PermissionMessage(RegionState.OCCUPIED, region.getForkliftSerialNumber());
                    }
                }
            }
        }
        return new PermissionMessage(RegionState.LACK, "");
    }

//    deep copy
    public List<Region> getRegionsList() {
        return regionList.getRegions();
    }

    public void freeRegions(String serialNumber) {
        for (Region region : regionList.getRegions()) {
            if (region.getForkliftSerialNumber().equals(serialNumber)) {
                region.setForkliftSerialNumber("");
            }
        }
    }

    public boolean isForkliftOutside(Forklift forklift) {
        for (Region region : regionList.getRegions()) {
            if (region.getForkliftSerialNumber().equals(forklift.getSerialNumber()) || region.isForkliftInside(forklift.getCoords())) {
                return false;
            }
        }
        return true;
    }

    public void addRegion(Region region) {
        regionList.getRegions().add(region);
    }

    public ResponseEntity<Forklift> leaveRegionByForklift(ForkliftRequest forklift, RegionRequest regionToLeave) {
        for (Region region : regionList.getRegions()) {
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

    public boolean isForkliftInside(ForkliftRequest forkliftRequest) {
        for (Region region : regionList.getRegions()) {
            if (region.isForkliftInside(forkliftRequest.getCoords())) {
                return true;
            }
        }
        return false;
    }
}
