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
    public void registrationOutsideRegion() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(10, 10));
        regionForklift.setForklift(forklift);
//        regionForklift.setRegion(null);
//        assertEquals(new ResponseEntity<>(HttpStatus.CREATED), forkliftController.turnOffForklift(regionForklift));
//        forklift.setCoords(new Point(20, 20));
//        regionForklift.setForklift(forklift);
//        assertEquals(new ResponseEntity<>(HttpStatus.CREATED), forkliftController.turnOffForklift(regionForklift));
//
//        assertEquals(forkliftController.getForklift("Mirek"), forklift);
//        forklift.setSerialNumber(null);
//        regionForklift.setForklift(forklift);
//        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), forkliftController.turnOffForklift(regionForklift));
//
//        forklift.setCoords(null);
//        regionForklift.setForklift(forklift);
//        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), forkliftController.turnOffForklift(regionForklift));
//
//        regionForklift.setForklift(null);
//        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), forkliftController.turnOffForklift(regionForklift));
    }

    @Test
    public void registrationInsideRegion() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forklift1 = new ForkliftRequest("Mirek", new Point(53, 53));
        regionForklift.setForklift(forklift1);
        Region region = regionListService.getRegionsList().get(0);
//        regionForklift.setRegion(region);
//        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), forkliftController.addForklift(regionForklift));
//        region = regionListService.getRegionsList().get(1);
//        regionForklift.setRegion(region);
//        assertEquals(new ResponseEntity<>(HttpStatus.CREATED), forkliftController.addForklift(regionForklift));
//
//        Forklift forklift2 = new Forklift("Tymek", new Point(52, 52));
//        regionForklift.setForklift(forklift2);
//        assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), forkliftController.addForklift(regionForklift));
//
//        int[] xpoints = {200, 200, 210, 210};
//        int[] ypoints = {200, 210, 210, 200};
//        region.setPolygon(new Polygon(xpoints, ypoints, 4));
//        regionForklift.setRegion(region);
//        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), forkliftController.addForklift(regionForklift));
//
//        region = regionService.getRegionsList().get(0);
//        region.setId(2);
//        regionForklift.setRegion(region);
//        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), forkliftController.addForklift(regionForklift));
    }

    @Test
    public void inactiveForkliftTests() {
        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(20, 20));
        RegionForklift regionForklift = new RegionForklift();
        regionForklift.setForklift(forklift);
        regionForklift.setRegion(null);
        forkliftController.turnOnForklift(regionForklift);
        forkliftController.checker();
//        assertEquals(ForkliftState.ACTIVE, forkliftController.getForklift(forklift.getSerialNumber()).getState());
//        forklift.setLastConnection(new Date(forklift.getLastConnection().getTime() - 30001));
//        forkliftController.checker();
//        assertEquals(ForkliftState.ACTIVE, forkliftController.getForklift(forklift.getSerialNumber()).getState());
//        forklift.setLastConnection(new Date(new Date().getTime() - 90001));
//        forkliftController.checker();
//        assertEquals(ForkliftState.INACTIVE, forkliftController.getForklift(forklift.getSerialNumber()).getState());
//        forklift.setCoords(new Point(0, 0));
//        forkliftController.updateForklift(forklift);
//        forklift.setLastConnection(new Date(new Date().getTime() - 30001));
//        forkliftController.checker();
//        assertEquals(ForkliftState.INACTIVE, forkliftController.getForklift(forklift.getSerialNumber()).getState());
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
    void leaveTheRegion() {

    }
}