package com.example.forkliftrestserver.service;

import com.example.forkliftrestserver.model.Forklift;
import com.example.forkliftrestserver.model.ForkliftState;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ForkliftService {
    private Map<String, Forklift> forkliftMap = new HashMap<>();

    public Map<String, Forklift> getForklifts() {
        return forkliftMap;
    }

    public void updateForklift(String serialNumber) {
        Forklift forklift = forkliftMap.get(serialNumber);
        if(forklift != null){
            forklift.setLastConnection(new Date());
            forkliftMap.replace(serialNumber, forklift);
        }
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
        forkliftMap.putIfAbsent(forklift.getSerialNumber(), forklift);
    }
}
