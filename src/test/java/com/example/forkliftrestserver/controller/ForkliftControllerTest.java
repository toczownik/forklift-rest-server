package com.example.forkliftrestserver.controller;

import com.example.forkliftrestserver.model.*;
import com.example.forkliftrestserver.service.ForkliftService;
import com.example.forkliftrestserver.service.RegionListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ForkliftControllerTest {

    private RegionRequest region =  new RegionRequest();
    private Region regionToList = new Region();
    private RegionList regionList = new RegionList();
    private RegionListService regionListService = new RegionListService();
    private ForkliftController forkliftController = new ForkliftController(new ForkliftService(), regionListService);

    @BeforeEach
    void init(){
        int[] xpoints = {100, 100, 110, 110};
        int[] ypoints = {100, 110, 110, 100};
        region.setId(0);
        region.setPolygonIn(new Polygon(xpoints, ypoints, 4));
        region.setPolygonOut(new Polygon(xpoints, ypoints, 4));

        regionToList.setId(0);
        regionToList.setPolygonIn(new Polygon(xpoints, ypoints, 4));
        regionToList.setPolygonOut(new Polygon(xpoints, ypoints, 4));
        regionToList.setForkliftSerialNumber("");
        regionList.setRegions(new ArrayList<>());
        regionList.getRegions().add(regionToList);

        regionListService.getRegionsList().clear();
        regionListService.getRegionsList().add(regionToList);

    }

    @Test
    public void turnOnForkliftOutsideCREATED() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(10, 10));
        regionForklift.setForklift(forklift);
        regionForklift.setRegion(null);
        assertEquals(new ResponseEntity<>(HttpStatus.CREATED), forkliftController.turnOnForklift(regionForklift));
    }

    @Test
    public void turnOnForkliftRegionForkliftNullBADREQUEST() {
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), forkliftController.turnOnForklift(null));
    }

    @Test
    public void turnOnForkliftForkliftNullBADREQUEST() {
        RegionForklift regionForklift = new RegionForklift();
        regionForklift.setForklift(null);
        regionForklift.setRegion(new RegionRequest());
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), forkliftController.turnOnForklift(regionForklift));
    }

    @Test
    public void turnOnForkliftInsideCREATED() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forkliftRequest = new ForkliftRequest("Mirek", new Point(103, 103));
        RegionRequest regionRequest = new RegionRequest(0, region.getPolygonIn(), region.getPolygonOut());
        regionForklift.setForklift(forkliftRequest);
        regionForklift.setRegion(regionRequest);
        assertEquals(new ResponseEntity<>(HttpStatus.CREATED), forkliftController.turnOnForklift(regionForklift));
    }

    @Test
    public void turnOnForkliftInsideFORBIDDEN() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forkliftRequest = new ForkliftRequest("Mirek", new Point(103, 103));
        RegionRequest regionRequest = new RegionRequest(0, region.getPolygonIn(), region.getPolygonOut());
        regionForklift.setForklift(forkliftRequest);
        regionForklift.setRegion(regionRequest);
        forkliftController.turnOnForklift(regionForklift);
        forkliftRequest = new ForkliftRequest("Spark", new Point(104, 103));
        regionForklift.setForklift(forkliftRequest);
        assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), forkliftController.turnOnForklift(regionForklift));
    }

    @Test
    public void turnOnForkliftInsideNOTFOUND() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forkliftRequest = new ForkliftRequest("Mirek", new Point(103, 103));
        RegionRequest regionRequest = new RegionRequest(1, region.getPolygonIn(), region.getPolygonOut());
        regionForklift.setForklift(forkliftRequest);
        regionForklift.setRegion(regionRequest);
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), forkliftController.turnOnForklift(regionForklift));
    }

    @Test
    public void checkActiveForklift() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forkliftRequest = new ForkliftRequest("Mirek", new Point(103, 103));
        RegionRequest regionRequest = new RegionRequest(0, region.getPolygonIn(), region.getPolygonOut());
        regionForklift.setForklift(forkliftRequest);
        regionForklift.setRegion(regionRequest);
        forkliftController.turnOnForklift(regionForklift);
        forkliftController.checker();
        assertEquals(ForkliftState.ACTIVE, forkliftController.getForklifts().get("Mirek").getState());
    }

    @Test
    public void checkInactiveForkliftOutside() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forkliftRequest = new ForkliftRequest("Mirek", new Point(10, 10));
        RegionRequest regionRequest = new RegionRequest(0, region.getPolygonIn(), region.getPolygonOut());
        regionForklift.setForklift(forkliftRequest);
        regionForklift.setRegion(regionRequest);
        forkliftController.turnOnForklift(regionForklift);
        forkliftController.getForklifts().get("Mirek").setLastConnection(new Date(new Date().getTime() - 30001));
        forkliftController.checker();
        assertEquals(ForkliftState.INACTIVE, forkliftController.getForklifts().get("Mirek").getState());
    }

    @Test
    public void checkInactiveForkliftInside() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forkliftRequest = new ForkliftRequest("Mirek", new Point(103, 103));
        RegionRequest regionRequest = new RegionRequest(0, region.getPolygonIn(), region.getPolygonOut());
        regionForklift.setForklift(forkliftRequest);
        regionForklift.setRegion(regionRequest);
        forkliftController.turnOnForklift(regionForklift);
        forkliftController.getForklifts().get("Mirek").setLastConnection(new Date(new Date().getTime() - 30001));
        forkliftController.checker();
        assertEquals(ForkliftState.ACTIVE, forkliftController.getForklifts().get("Mirek").getState());
        forkliftController.getForklifts().get("Mirek").setLastConnection(new Date(new Date().getTime() - 90001));
        forkliftController.checker();
        for (Region region: regionListService.getRegionsList()) {
            assertNotEquals(forkliftController.getForklifts().get("Mirek").getSerialNumber(), region.getForkliftSerialNumber());
        }
        assertEquals(ForkliftState.INACTIVE, forkliftController.getForklifts().get("Mirek").getState());
    }

    @Test
    void getPermissionToRegionResponseOK() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(105, 105));
        regionForklift.setForklift(forklift);
        regionForklift.setRegion(region);

        assertEquals(new ResponseEntity<>(HttpStatus.OK), forkliftController.getPermissionToRegion(regionForklift));
    }

    @Test
    void getPermissionToRegionResponseFORBIDDEN() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(105, 105));
        regionForklift.setForklift(forklift);
        regionToList.setForkliftSerialNumber("Spark");
        regionForklift.setRegion(region);
        ResponseEntity<String> response = forkliftController.getPermissionToRegion(regionForklift);

        assertEquals(new ResponseEntity<>(regionToList.getForkliftSerialNumber(), HttpStatus.FORBIDDEN), response);
    }


    @Test
    void getPermissionToRegionResponseNOTFOUND() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(500, 500));
        regionForklift.setForklift(forklift);
        regionForklift.setRegion(region);
        ResponseEntity<String> response = forkliftController.getPermissionToRegion(regionForklift);
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), response);
    }

    @Test
    void leaveTheRegionNullBADREQUEST() {
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), forkliftController.leaveTheRegion(null));
    }

    @Test
    void leaveTheRegionForkliftNullBADREQUEST() {
        RegionForklift regionForklift = new RegionForklift();

        regionForklift.setForklift(null);
        regionForklift.setRegion(region);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), forkliftController.leaveTheRegion(regionForklift));
    }

    @Test
    void leaveTheRegionRegionNullBADREQUEST() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forkliftRequest = new ForkliftRequest("Spark", new Point(200, 200));

        regionForklift.setForklift(forkliftRequest);
        regionForklift.setRegion(null);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), forkliftController.leaveTheRegion(regionForklift));
    }

    @Test
    void leaveTheRegionOK() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forkliftRequest = new ForkliftRequest("Mirek", new Point(200, 200));

        regionList.getRegions().get(0).setForkliftSerialNumber("Mirek");
        regionForklift.setForklift(forkliftRequest);
        regionForklift.setRegion(region);
        assertEquals(new ResponseEntity<>(HttpStatus.OK), forkliftController.leaveTheRegion(regionForklift));
    }

    @Test
    void leaveTheRegionForkliftNotFoundNOTFOUND() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forkliftRequest = new ForkliftRequest("Mirek", new Point(200, 200));

        regionList.getRegions().get(0).setForkliftSerialNumber("Spark");
        regionForklift.setForklift(forkliftRequest);
        regionForklift.setRegion(region);
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), forkliftController.leaveTheRegion(regionForklift));
    }

    @Test
    void leaveTheRegionForkliftInsideNOTFOUND() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forkliftRequest = new ForkliftRequest("Mirek", new Point(105, 105));

        regionList.getRegions().get(0).setForkliftSerialNumber("Mirek");
        regionForklift.setForklift(forkliftRequest);
        regionForklift.setRegion(region);
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), forkliftController.leaveTheRegion(regionForklift));
    }
}
