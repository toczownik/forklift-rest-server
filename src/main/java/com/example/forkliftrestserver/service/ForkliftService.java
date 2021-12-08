package com.example.forkliftrestserver.service;

import com.example.forkliftrestserver.model.Forklift;
import com.example.forkliftrestserver.model.ForkliftState;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ForkliftService {
    private Map<String, Forklift> forkliftMap = new HashMap<>();

    public Map<String, Forklift> getForklifts() {
        return forkliftMap;
    }

    public void updateForklift(Forklift forklift) {
        forklift.updateLastConnection();
        forkliftMap.put(forklift.getSerialNumber(), forklift);
    }

    public void removeForklift(String id) {
        forkliftMap.get(id).setState(ForkliftState.INACTIVE);
        forkliftMap.remove(id);
    }

    public Forklift getForkliftBySerialNumber(String serialNumber) {
        return forkliftMap.get(serialNumber);
    }

    public boolean forkliftExists(String serialNumber) {
        return forkliftMap.containsKey(serialNumber);
    }

    public void addForklift(Forklift forklift, ForkliftState state) {
        forklift.setLastConnection(new Date());
        forklift.setState(state);
        if (!forkliftMap.containsKey(forklift.getSerialNumber())) {
            forkliftMap.put(forklift.getSerialNumber(), forklift);
        }
    }
}
