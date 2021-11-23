package com.example.forkliftrestserver.service;

import com.example.forkliftrestserver.model.Forklift;
import com.example.forkliftrestserver.model.ForkliftState;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@EnableScheduling
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

    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void checker() {
        for (Forklift forklift:
                forkliftMap.values()) {
            long timeDiff = new Date().getTime() - forklift.getLastConnection().getTime();
            if (timeDiff >= 5000) {
                if (forklift.getState() == ForkliftState.INACTIVE && timeDiff > 30000) {
                    removeForklift(forklift.getSerialNumber());
                    // TODO: uwolnienie zajętych semaforów
                }
                forklift.setState(ForkliftState.INACTIVE);
            }
        }
    }

}
