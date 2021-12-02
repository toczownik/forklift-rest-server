package com.example.forkliftrestserver.controller;

import com.example.forkliftrestserver.model.*;
import com.example.forkliftrestserver.service.ForkliftService;
import com.example.forkliftrestserver.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.Map;

@RestController
@EnableScheduling
@RequestMapping("/forklift")
public class ForkliftController {

    private ForkliftService forkliftService;
    private RegionService regionService;

    @Autowired
    public ForkliftController(ForkliftService forkliftService, RegionService regionService) {
        this.forkliftService = forkliftService;
        this.regionService = regionService;
    }

    @GetMapping("/all")
    public Map<String, Forklift> getForklifts() {
        return forkliftService.getForklifts();
    }

    @GetMapping("/{serialNumber}")
    public Forklift getForklift(@PathVariable("serialNumber") String serialNumber) {
        return forkliftService.getForkliftBySerialNumber(serialNumber);
    }

    @PostMapping("/update")
    public ResponseEntity<Forklift> updateForklift(@RequestBody Forklift forklift) {
        forkliftService.updateForklift(forklift);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<Forklift> addForklift(@RequestBody Forklift forklift) {
        forkliftService.addForklift(forklift);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/getPermission")
    synchronized public ResponseEntity<String> getPremissionToRegion(@RequestBody RegionForklift regionForklift) {
        PermissionMessage permission = regionService.getPermission(regionForklift.getForklift(), regionForklift.getRegion());
        if (permission.getStatus() == RegionState.SUCCESS) {
            forkliftService.updateForklift(regionForklift.getForklift());
            return new ResponseEntity<>(HttpStatus.OK);
        } else if(permission.getStatus() == RegionState.OCCUPIED){
            return new ResponseEntity<>(permission.getForkliftSerialNumber(), HttpStatus.BAD_REQUEST);
        }else if(permission.getStatus() == RegionState.LACK){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/leaveTheRegion")
    synchronized public ResponseEntity<Forklift> leaveTheRegion(@RequestBody RegionForklift regionForklift) {
        return regionService.leaveRegionByForklift(regionForklift.getForklift(), regionForklift.getRegion());
    }

    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<Forklift> deleteForklift(@PathVariable("serialNumber") String serialNumber) {
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
                        forkliftService.removeForklift(forklift.getSerialNumber()); // czy usuwamy od razu cały wózek z listy czy ustawiamy jako nieaktywny ?
                        // zgodnie z założeniami mamy ustawić go na nieaktywny i zwolnić region
                    }
                }
                forklift.setState(ForkliftState.INACTIVE);
            }
        }
    }
}
