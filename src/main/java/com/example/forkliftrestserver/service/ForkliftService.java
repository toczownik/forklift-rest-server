package com.example.forkliftrestserver.service;

import com.example.forkliftrestserver.model.Forklift;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ForkliftService {
    private Map<String, Forklift> forkliftMap = new HashMap<>();

    public Map<String, Forklift> getForklifts() {
        return forkliftMap;
    }

    public void addForklift(Forklift forklift) {
        forklift.updateLastConnection();
        forkliftMap.put(forklift.getSerialNumber(), forklift);
    }

    public void removeForklift(String id) {
        forkliftMap.remove(id);
    }

    public Forklift getForkliftBySerialNumber(String serialNumber) {
        return forkliftMap.get(serialNumber);
    }

    public boolean forkliftExists(String serialNumber) {
        return forkliftMap.containsKey(serialNumber);
    }

}
