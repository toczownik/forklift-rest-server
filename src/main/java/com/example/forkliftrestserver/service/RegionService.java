package com.example.forkliftrestserver.service;

import com.example.forkliftrestserver.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.net.URL;
import java.util.List;

@Service
public class RegionService {
    private RegionList regionList;

    public RegionService() {
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

    synchronized public PermissionMessage getPermission(Forklift forklift, Region region) {
        return regionList.isForkliftMovementAllowed(forklift, region);
    }

    public List<Region> getRegionsList() {
        return regionList.getRegions();
    }

    public void freeRegions(String serialNumber) {
        regionList.freeAssignedRegions(serialNumber);
    }

    public void addRegion(Region region) {
        regionList.addRegion(region);
    }

    public ResponseEntity<Forklift> leaveRegionByForklift(Forklift forklift, Region regionToLeave) {
        return regionList.leaveRegionByForklift(forklift, regionToLeave);
    }

    public boolean isForkliftOutside(Forklift forklift) {
        return regionList.anyTakenByForklift(forklift);
    }
}
