package com.example.forkliftrestserver.service;

import com.example.forkliftrestserver.model.Forklift;
import com.example.forkliftrestserver.model.Region;
import com.example.forkliftrestserver.model.RegionList;
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

    synchronized public boolean getPermission(Forklift forklift, int regionID) {
        return regionList.isForkliftMovementAllowed(forklift, regionID);
    }

    public List<Region> getRegionsList() {
        return regionList.getRegions();
    }

    public void freeRegions(int serialNumber) {
        regionList.freeAssignedRegions(serialNumber);
    }
}
