package com.example.forkliftrestserver.service;

import com.example.forkliftrestserver.model.Forklift;
import com.example.forkliftrestserver.model.ForkliftRequest;
import com.example.forkliftrestserver.model.ForkliftState;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ForkliftService {
    private Map<String, Forklift> forkliftMap = new HashMap<>();

    public Map<String, Forklift> getForklifts() {
        return forkliftMap;
    }

    public void updateForklift(ForkliftRequest forkliftRequest, ForkliftState state) {
        Forklift forklift = forkliftMap.get(forkliftRequest.getSerialNumber());
        if(forklift != null){
            forklift.setLastConnection(new Date());
            forklift.setCoords(forkliftRequest.getCoords());
            forklift.setState(state);
            forkliftMap.replace(forklift.getSerialNumber(), forklift);
        }
    }

    public void updateForklift(ForkliftRequest forkliftRequest) {
        Forklift forklift = forkliftMap.get(forkliftRequest.getSerialNumber());
        if(forklift != null){
            forklift.setLastConnection(new Date());
            forklift.setCoords(forkliftRequest.getCoords());
            forkliftMap.replace(forklift.getSerialNumber(), forklift);
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

    public void addForklift(ForkliftRequest forkliftRequest, ForkliftState state) {
        Forklift newForklift = new Forklift(forkliftRequest.getSerialNumber(), forkliftRequest.getCoords());
        if(newForklift != null) {
            newForklift.setLastConnection(new Date());
            newForklift.setState(state);
            forkliftMap.putIfAbsent(newForklift.getSerialNumber(), newForklift);
        }
    }
}
