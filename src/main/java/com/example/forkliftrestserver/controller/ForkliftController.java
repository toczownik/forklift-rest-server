package com.example.forkliftrestserver.controller;

import com.example.forkliftrestserver.model.*;
import com.example.forkliftrestserver.service.ForkliftService;
import com.example.forkliftrestserver.service.RegionListService;
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
    private RegionListService regionListService;

    @Autowired
    public ForkliftController(ForkliftService forkliftService, RegionListService regionListService) {
        this.forkliftService = forkliftService;
        this.regionListService = regionListService;
    }

    @GetMapping("/all")
    public Map<String, Forklift> getForklifts() {
        return forkliftService.getForklifts();
    }

    @GetMapping("/{serialNumber}")
    public synchronized ResponseEntity<Forklift> getForklift(@PathVariable("serialNumber") String serialNumber) {
        if(serialNumber == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Forklift forklift = forkliftService.getForkliftBySerialNumber(serialNumber);
        if(forklift != null){
            return new ResponseEntity<>(forklift, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/update")
    public synchronized ResponseEntity<Forklift> updateForklift(@RequestBody ForkliftRequest forkliftRequest) {
        if (forkliftRequest == null || forkliftRequest.getSerialNumber() == null ||
                forkliftRequest.getCoords() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        forkliftService.updateForklift(forkliftRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/turnOnForklift")
    public synchronized ResponseEntity<Forklift> turnOnForklift(@RequestBody RegionForklift regionForklift) {
        if (regionForklift == null || regionForklift.getForklift() == null || regionForklift.getForklift().getSerialNumber() == null ||
                regionForklift.getForklift().getCoords() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (regionForklift.getRegion() != null) {
            PermissionMessage permission = regionListService.getPermission(
                    regionForklift.getForklift(), regionForklift.getRegion());
            switch (permission.getStatus()) {
                case SUCCESS:
                    break;
                case OCCUPIED:
                    forkliftService.addForklift(regionForklift.getForklift(), ForkliftState.WAITING);
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                case LACK:
                    forkliftService.addForklift(regionForklift.getForklift(), ForkliftState.INACTIVE);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                default:
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        forkliftService.addForklift(regionForklift.getForklift(), ForkliftState.ACTIVE);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/getPermission")
    public synchronized ResponseEntity<String> getPermissionToRegion(@RequestBody RegionForklift regionForklift) {
        if (regionForklift == null || regionForklift.getForklift() == null || regionForklift.getForklift().getSerialNumber() == null ||
                regionForklift.getForklift().getCoords() == null || regionForklift.getRegion() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        PermissionMessage permission = regionListService.getPermission(regionForklift.getForklift(), regionForklift.getRegion());

        switch (permission.getStatus()) {
            case SUCCESS:
                forkliftService.updateForklift(regionForklift.getForklift(), ForkliftState.ACTIVE);
                return new ResponseEntity<>(HttpStatus.OK);
            case OCCUPIED:
                forkliftService.updateForklift(regionForklift.getForklift(), ForkliftState.WAITING);
                return new ResponseEntity<>(permission.getForkliftSerialNumber(), HttpStatus.FORBIDDEN);
            case LACK:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/leaveTheRegion")
    public synchronized ResponseEntity<Forklift> leaveTheRegion(@RequestBody RegionForklift regionForklift) {
        if (regionForklift == null || regionForklift.getForklift() == null || regionForklift.getForklift().getSerialNumber() == null ||
                regionForklift.getForklift().getCoords() == null || regionForklift.getRegion() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return regionListService.leaveRegionByForklift(regionForklift.getForklift(), regionForklift.getRegion());
    }

    @PostMapping("/turnOff")
    public synchronized ResponseEntity<String> turnOffForklift(@RequestBody ForkliftRequest forkliftRequest) {
        if (forkliftRequest == null || forkliftRequest.getSerialNumber() == null ||
                forkliftRequest.getCoords() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (forkliftService.forkliftExists(forkliftRequest.getSerialNumber())) {
            regionListService.freeRegions(forkliftRequest.getSerialNumber());
            boolean isInside = regionListService.isForkliftInside(forkliftRequest);
            forkliftService.updateForklift(forkliftRequest, ForkliftState.INACTIVE);
            if (isInside) {
                return new ResponseEntity<>("Please move forklift out of region.", HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 5000)
    public void checker() {
        for (Forklift forklift: forkliftService.getForklifts().values()) {
            long timeDiff = new Date().getTime() - forklift.getLastConnection().getTime();

            if (timeDiff > 30000) {
                if (regionListService.isForkliftOutside(forklift)) {
                    forklift.setState(ForkliftState.INACTIVE);
                } else if (timeDiff > 90000) {
                    regionListService.freeRegions(forklift.getSerialNumber());
                    forklift.setState(ForkliftState.INACTIVE);
                }
            }
        }
    }
}
