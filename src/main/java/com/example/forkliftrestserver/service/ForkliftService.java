package com.example.forkliftrestserver.service;

import com.example.forkliftrestserver.model.Forklift;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ForkliftService {
    private Map<Integer, Forklift> forkliftMap = new HashMap<>();

    public Map<Integer, Forklift> getForklifts() {
        return forkliftMap;
    }

    public void addForklift(Forklift forklift) {
        forklift.updateLastConnection();
        forkliftMap.put(forklift.getSerialNumber(), forklift);
    }

    public void removeForklift(int id) {
        forkliftMap.remove(id);
    }

    public Forklift getForkliftBySerialNumber(int serialNumber) {
        return forkliftMap.get(serialNumber);
    }

    public boolean forkliftExists(int serialNumber) {
        return forkliftMap.containsKey(serialNumber);
    }

}
