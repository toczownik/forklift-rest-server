package com.example.forkliftrestserver.controller;

import com.example.forkliftrestserver.model.Forklift;
import com.example.forkliftrestserver.model.Region;
import com.example.forkliftrestserver.service.ForkliftService;
import com.example.forkliftrestserver.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
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
        forkliftService.addForklift(forklift);
        boolean hasPermission = regionService.getPermission(forklift);
        if (hasPermission) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
