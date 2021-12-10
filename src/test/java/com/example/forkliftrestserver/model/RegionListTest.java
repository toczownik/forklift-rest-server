package com.example.forkliftrestserver.model;

import com.example.forkliftrestserver.service.RegionListService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class RegionListTest {

    private RegionListService regionListService = new RegionListService();

    @Test
    void isForkliftMovementAllowed() {
    }

    @Test
    void isForkliftMovementAllowedToFreeRegion(){
//        RegionForklift regionForklift = new RegionForklift();
//        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(105, 105));
//        regionForklift.setForklift(forklift);
//        Region region = regionListService.getRegionsList().get(0);
//        regionForklift.setRegion(region);
//        PermissionMessage message = regionListService.getPermission(forklift, region);
//        PermissionMessage expectedMessage = new PermissionMessage(RegionState.SUCCESS, forklift.getSerialNumber());
//        assertEquals(expectedMessage.toString(), message.toString());
    }

    @Test
    void isForkliftMovementAllowedToOccupiedRegion(){
//        RegionForklift regionForklift = new RegionForklift();
//        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(105, 105));
//        regionForklift.setForklift(forklift);
//        Region region = regionListService.getRegionsList().get(0);
//        region.setForkliftSerialNumber("Spark");
//        regionForklift.setRegion(region);
//        PermissionMessage message = regionListService.getPermission(forklift, region);
//        PermissionMessage expectedMessage = new PermissionMessage(RegionState.OCCUPIED, region.getForkliftSerialNumber());
//        assertEquals(expectedMessage.toString(), message.toString());
    }

    @Test
    void isForkliftMovementAllowedToLackdRegion(){
//        RegionForklift regionForklift = new RegionForklift();
//        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(350, 350));
//        regionForklift.setForklift(forklift);
//        Region region = regionListService.getRegionsList().get(0);
//        regionForklift.setRegion(region);
//        PermissionMessage message = regionListService.getPermission(forklift, region);
//        PermissionMessage expectedMessage = new PermissionMessage(RegionState.LACK, "");
//        assertEquals(expectedMessage.toString(), message.toString());
    }

    @Test
    void freeAssignedRegions() {
    }

    @Test
    void addRegion() {
    }

    @Test
    void leaveRegionByForkliftResponseOK() {
//        RegionForklift regionForklift = new RegionForklift();
//        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(205, 205));
//        regionForklift.setForklift(forklift);
//        Region region = regionListService.getRegionsList().get(0);
//        regionForklift.setRegion(region);
//        region.setId(1);
//        region.setForkliftSerialNumber("Mirek");
//        ResponseEntity<Forklift> response = regionListService.leaveRegionByForklift(forklift, region);
//        ResponseEntity<Forklift> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
//        assertEquals(expectedResponse.toString(), response.toString());

    }

    @Test
    void leaveRegionByForkliftDifferentSerialNumberResponseNOTFOUND() {
//        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(205, 205));
//        Region region = new Region();
//
//        int[] xpoints = {100, 100, 110, 110};
//        int[] ypoints = {100, 110, 110, 100};
//        region.setPolygonIn(new Polygon(xpoints, ypoints, 4));
//        region.setId(1);
//        region.setForkliftSerialNumber("Spark");
//        ResponseEntity<Forklift> response = regionListService.leaveRegionByForklift(forklift, region);
//        ResponseEntity<Forklift> expectedResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        assertEquals(expectedResponse.toString(), response.toString());
    }

    @Test
    void leaveRegionByForkliftDifferentRegionIdResponseNOTFOUND() {
//        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(205, 205));
//        Region region = new Region();
//
//        int[] xpoints = {200, 200, 210, 210};
//        int[] ypoints = {200, 210, 210, 200};
//        region.setPolygonIn(new Polygon(xpoints, ypoints, 4));
//        region.setId(5);
//        region.setForkliftSerialNumber("Mirek");
//
//        ResponseEntity<Forklift> response = regionListService.leaveRegionByForklift(forklift, region);
//        ResponseEntity<Forklift> expectedResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        assertEquals(expectedResponse.toString(), response.toString());
    }
}