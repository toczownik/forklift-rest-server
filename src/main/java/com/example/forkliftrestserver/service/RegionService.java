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
        Constructor semaphoreListConstructor = new Constructor(RegionList.class);
        TypeDescription semaphoreDescription = new TypeDescription(Region.class);
        semaphoreDescription.addPropertyParameters("semaphores", Region.class, Object.class);
        semaphoreListConstructor.addTypeDescription(semaphoreDescription);
        Yaml yaml = new Yaml();
        try {
            regionList = yaml.load(resource.openStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getPermission(Forklift forklift) {
        return regionList.isForkliftMovementAllowed(forklift);
    }

    public List<Region> getSemaphoreList() {
        return regionList.getRegions();
    }

    public void freeRegions(int serialNumber) {
        regionList.freeAssignedRegions(serialNumber);
    }
}
