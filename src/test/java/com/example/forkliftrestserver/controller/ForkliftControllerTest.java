package com.example.forkliftrestserver.controller;

import com.example.forkliftrestserver.model.Forklift;
import com.example.forkliftrestserver.model.Region;
import com.example.forkliftrestserver.model.RegionForklift;
import com.example.forkliftrestserver.service.ForkliftService;
import com.example.forkliftrestserver.service.RegionService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ForkliftControllerTest {

    private RegionService regionService = new RegionService();
    private ForkliftController forkliftController = new ForkliftController(new ForkliftService(), regionService);

    @Test
    void getPermissionToRegionResponseOK() {
        RegionForklift regionForklift = new RegionForklift();
        Forklift forklift = new Forklift("Mirek", new Point(105, 105));
        regionForklift.setForklift(forklift);
        Region region = regionService.getRegionsList().get(0);
        regionForklift.setRegion(region);

        assertEquals(new ResponseEntity<>(HttpStatus.OK), forkliftController.getPermissionToRegion(regionForklift));
    }

    @Test
    void getPermissionToRegionResponseFORBIDDEN() {
        RegionForklift regionForklift = new RegionForklift();
        Forklift forklift = new Forklift("Mirek", new Point(105, 105));
        regionForklift.setForklift(forklift);
        Region region = regionService.getRegionsList().get(0);
        region.setForkliftSerialNumber("Spark");
        regionForklift.setRegion(region);
        ResponseEntity<String> response = forkliftController.getPermissionToRegion(regionForklift);
        assertEquals(new ResponseEntity<>(region.getForkliftSerialNumber(), HttpStatus.FORBIDDEN), response);
    }


    @Test
    void getPermissionToRegionResponseNOTFOUND() {
        RegionForklift regionForklift = new RegionForklift();
        Forklift forklift = new Forklift("Mirek", new Point(500, 500));
        regionForklift.setForklift(forklift);
        Region region = regionService.getRegionsList().get(0);
        regionForklift.setRegion(region);
        ResponseEntity<String> response = forkliftController.getPermissionToRegion(regionForklift);
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), response);
    }

    @Test
    void leaveTheRegion() {

    }
}