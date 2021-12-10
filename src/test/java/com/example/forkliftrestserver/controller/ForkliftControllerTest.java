package com.example.forkliftrestserver.controller;

import com.example.forkliftrestserver.model.Forklift;
import com.example.forkliftrestserver.model.ForkliftRequest;
import com.example.forkliftrestserver.model.Region;
import com.example.forkliftrestserver.model.RegionForklift;
import com.example.forkliftrestserver.service.ForkliftService;
import com.example.forkliftrestserver.service.RegionListService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ForkliftControllerTest {

    private RegionListService regionListService = new RegionListService();
    private ForkliftController forkliftController = new ForkliftController(new ForkliftService(), regionListService);

    @Test
    public void registrationOutsideRegion() {
//        RegionForklift regionForklift = new RegionForklift();
//        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(10, 10));
//        regionForklift.setForklift(forklift);
//        regionForklift.setRegion(null);
//        assertEquals(new ResponseEntity<>(HttpStatus.CREATED), forkliftController.addForklift(regionForklift));
//        forklift.setCoords(new Point(20, 20));
//        regionForklift.setForklift(forklift);
//        assertEquals(new ResponseEntity<>(HttpStatus.CREATED), forkliftController.addForklift(regionForklift));
//
//        assertEquals(forkliftController.getForklift("Mirek"), forklift);
//        forklift.setSerialNumber(null);
//        regionForklift.setForklift(forklift);
//        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), forkliftController.addForklift(regionForklift));
//
//        forklift.setCoords(null);
//        regionForklift.setForklift(forklift);
//        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), forkliftController.addForklift(regionForklift));
//
//        regionForklift.setForklift(null);
//        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), forkliftController.addForklift(regionForklift));
    }

    @Test
    public void registrationInsideRegion() {
//        RegionForklift regionForklift = new RegionForklift();
//        ForkliftRequest forklift1 = new ForkliftRequest("Mirek", new Point(53, 53));
//        regionForklift.setForklift(forklift1);
//        Region region = regionListService.getRegionsList().get(0);
//        regionForklift.setRegion(region);
//        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), forkliftController.addForklift(regionForklift));
//        region = regionListService.getRegionsList().get(1);
//        regionForklift.setRegion(region);
//        assertEquals(new ResponseEntity<>(HttpStatus.CREATED), forkliftController.addForklift(regionForklift));
//
//        ForkliftRequest forklift2 = new ForkliftRequest("Tymek", new Point(52, 52));
//        regionForklift.setForklift(forklift2);
//        assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), forkliftController.addForklift(regionForklift));
//
//        int[] xpoints = {200, 200, 210, 210};
//        int[] ypoints = {200, 210, 210, 200};
//        region.setPolygonIn(new Polygon(xpoints, ypoints, 4));
//        regionForklift.setRegion(region);
//        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), forkliftController.addForklift(regionForklift));
//
//        region = regionListService.getRegionsList().get(0);
//        region.setId(2);
//        regionForklift.setRegion(region);
//        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), forkliftController.addForklift(regionForklift));
    }

    @Test
    void getPermissionToRegionResponseOK() {
//        RegionForklift regionForklift = new RegionForklift();
//        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(105, 105));
//        regionForklift.setForklift(forklift);
//        Region region = regionListService.getRegionsList().get(0);
//        regionForklift.setRegion(region);
//
//        assertEquals(new ResponseEntity<>(HttpStatus.OK), forkliftController.getPermissionToRegion(regionForklift));
    }

    @Test
    void getPermissionToRegionResponseFORBIDDEN() {
//        RegionForklift regionForklift = new RegionForklift();
//        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(105, 105));
//        regionForklift.setForklift(forklift);
//        Region region = regionListService.getRegionsList().get(0);
//        region.setForkliftSerialNumber("Spark");
//        regionForklift.setRegion(region);
//        ResponseEntity<String> response = forkliftController.getPermissionToRegion(regionForklift);
//        assertEquals(new ResponseEntity<>(region.getForkliftSerialNumber(), HttpStatus.FORBIDDEN), response);
    }


    @Test
    void getPermissionToRegionResponseNOTFOUND() {
//        RegionForklift regionForklift = new RegionForklift();
//        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(500, 500));
//        regionForklift.setForklift(forklift);
//        Region region = regionListService.getRegionsList().get(0);
//        regionForklift.setRegion(region);
//        ResponseEntity<String> response = forkliftController.getPermissionToRegion(regionForklift);
//        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), response);
    }

    @Test
    void leaveTheRegion() {

    }
}