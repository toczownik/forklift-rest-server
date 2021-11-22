package com.example.forkliftrestserver.controller;

import com.example.forkliftrestserver.model.Forklift;
import com.example.forkliftrestserver.model.Semaphore;
import com.example.forkliftrestserver.model.SemaphoreList;
import com.example.forkliftrestserver.service.ForkliftService;
import com.example.forkliftrestserver.service.SemaphoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

@RestController
public class ForkliftController {

    private ForkliftService forkliftService;
    private SemaphoreService semaphoreService;

    @Autowired
    public ForkliftController(ForkliftService forkliftService, SemaphoreService semaphoreService) {
        this.forkliftService = forkliftService;
        this.semaphoreService = semaphoreService;
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

    @PostMapping("/test")
    public ResponseEntity<Forklift> postForklift() {
        Forklift forklift = new Forklift(2, new Point(30, 30));
        forkliftService.addForklift(forklift);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/semaphores")
    public List<Semaphore> getSemaphoreList() {
        return semaphoreService.getSemaphoreList();
    }

    @PostMapping("/getPermission")
    public ResponseEntity<Forklift> postForkliftAndCheckSemaphores(@RequestBody Forklift forklift) {
        forkliftService.addForklift(forklift);
        boolean hasPermission = semaphoreService.getPermission(forklift);
        if (hasPermission) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
