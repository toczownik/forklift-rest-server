package com.example.forkliftrestserver.controller;

import com.example.forkliftrestserver.model.Forklift;
import com.example.forkliftrestserver.model.ForkliftState;
import com.example.forkliftrestserver.model.Region;
import com.example.forkliftrestserver.service.ForkliftService;
import com.example.forkliftrestserver.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@EnableScheduling
public class ForkliftController {

    private ForkliftService forkliftService;
    private RegionService regionService;

    @Autowired
    public ForkliftController(ForkliftService forkliftService, RegionService regionService) {
        this.forkliftService = forkliftService;
        this.regionService = regionService;
    }

    @GetMapping("/all")
    public Map<Integer, Forklift> getForklifts() {
        return forkliftService.getForklifts();
    }

    @GetMapping("/{serialNumber}")
    public Forklift getForklift(@PathVariable("serialNumber") int serialNumber) {
        return forkliftService.getForkliftBySerialNumber(serialNumber);
    }

    @PostMapping("/update")
    public ResponseEntity<Forklift> postForklift(@RequestBody Forklift forklift) {
        forkliftService.addForklift(forklift);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/semaphores")
    public List<Region> getSemaphoreList() {
        return regionService.getSemaphoreList();
    }

    @PostMapping("/getPermission")
    public ResponseEntity<Forklift> postForkliftAndCheckSemaphores(@RequestBody Forklift forklift) {
        boolean hasPermission = regionService.getPermission(forklift);
        if (hasPermission) {
            forkliftService.addForklift(forklift);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<Forklift> deleteForklift(@PathVariable("serialNumber") int serialNumber) {
        if (forkliftService.forkliftExists(serialNumber)) {
            regionService.freeRegions(serialNumber);
            forkliftService.removeForklift(serialNumber);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void checker() {
        for (Forklift forklift: forkliftService.getForklifts().values()) {
            long timeDiff = new Date().getTime() - forklift.getLastConnection().getTime();
            if (timeDiff >= 5000) {
                if (forklift.getState() == ForkliftState.INACTIVE && timeDiff > 10000) {
                    if (forklift.getTakenRegionsList().isEmpty()) {
                        forkliftService.removeForklift(forklift.getSerialNumber());
                    } else if (timeDiff > 30000) {
                        regionService.freeRegions(forklift.getSerialNumber());
                        forkliftService.removeForklift(forklift.getSerialNumber());
                    }
                }
                forklift.setState(ForkliftState.INACTIVE);
            }
        }
    }
}
