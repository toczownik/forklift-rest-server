package com.example.forkliftrestserver.model;

import com.example.forkliftrestserver.service.RegionListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegionListServiceTest {

    private RegionListService regionListService = new RegionListService();
    private RegionRequest region =  new RegionRequest();
    private Region regionToList = new Region();
    private RegionList regionList = new RegionList();

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
    void getPermissionToFreeRegion(){
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(105, 105));
        regionForklift.setForklift(forklift);
        regionForklift.setRegion(region);
        PermissionMessage message = regionListService.getPermission(forklift, region);
        PermissionMessage expectedMessage = new PermissionMessage(RegionState.SUCCESS, forklift.getSerialNumber());
        assertEquals(expectedMessage.toString(), message.toString());
    }

    @Test
    void getPermissionToOccupiedRegion(){
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(105, 105));
        regionForklift.setForklift(forklift);
        regionList.getRegions().get(0).setForkliftSerialNumber("Spark");
        regionForklift.setRegion(region);
        PermissionMessage message = regionListService.getPermission(forklift, region);
        PermissionMessage expectedMessage = new PermissionMessage(RegionState.OCCUPIED, regionList.getRegions().get(0).getForkliftSerialNumber());
        assertEquals(expectedMessage.toString(), message.toString());
    }

    @Test
    void getPermissionToLackdRegion(){
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(350, 350));
        regionForklift.setForklift(forklift);
        regionForklift.setRegion(region);
        PermissionMessage message = regionListService.getPermission(forklift, region);
        PermissionMessage expectedMessage = new PermissionMessage(RegionState.LACK, "");
        assertEquals(expectedMessage.toString(), message.toString());
    }

    @Test
    void leaveRegionByForkliftResponseOK() {
        RegionForklift regionForklift = new RegionForklift();
        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(205, 205));
        regionForklift.setForklift(forklift);
        regionList.getRegions().get(0).setForkliftSerialNumber("Mirek");

        ResponseEntity<Forklift> response = regionListService.leaveRegionByForklift(forklift, region);
        ResponseEntity<Forklift> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
        assertEquals(expectedResponse.toString(), response.toString());

    }

    @Test
    void leaveRegionByForkliftDifferentSerialNumberResponseNOTFOUND() {
        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(205, 205));

        regionList.getRegions().get(0).setForkliftSerialNumber("Spark");

        ResponseEntity<Forklift> response = regionListService.leaveRegionByForklift(forklift, region);
        ResponseEntity<Forklift> expectedResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        assertEquals(expectedResponse.toString(), response.toString());
    }

    @Test
    void leaveRegionByForkliftDifferentRegionIdResponseNOTFOUND() {
        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(205, 205));

        regionList.getRegions().get(0).setId(12);

        ResponseEntity<Forklift> response = regionListService.leaveRegionByForklift(forklift, region);
        ResponseEntity<Forklift> expectedResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        assertEquals(expectedResponse.toString(), response.toString());
    }

    @Test
    void isForkliftOutsideReturnTrue(){
        Forklift forklift = new Forklift("Mirek", new Point(205, 205));

        assertEquals(true, regionListService.isForkliftOutside(forklift));
    }

    @Test
    void isForkliftOutsideSerialNumberReturnFalse(){
        Forklift forklift = new Forklift("Mirek", new Point(205, 205));

        regionList.getRegions().get(0).setForkliftSerialNumber("Mirek");

        assertEquals(false, regionListService.isForkliftOutside(forklift));
    }

    @Test
    void isForkliftOutsideForkliftReturnFalse(){
        Forklift forklift = new Forklift("Mirek", new Point(105, 105));

        assertEquals(false, regionListService.isForkliftOutside(forklift));
    }

    @Test
    void isForkliftInsideReturnTrue(){
        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(105, 105));

        assertEquals(true, regionListService.isForkliftInside(forklift));
    }

    @Test
    void isForkliftInsideReturnFalse(){
        ForkliftRequest forklift = new ForkliftRequest("Mirek", new Point(205, 205));

        regionList.getRegions().get(0).setForkliftSerialNumber("Mirek");

        assertEquals(false, regionListService.isForkliftInside(forklift));
    }

    @Test
    void addCorrect(){
        int[] xpoints = {500, 500, 550, 550};
        int[] ypoints = {500, 550, 550, 500};
        Region newRegion = new Region();
        newRegion.setId(1);
        newRegion.setPolygonIn(new Polygon(xpoints, ypoints, 4));
        newRegion.setPolygonOut(new Polygon(xpoints, ypoints, 4));
        assertEquals(1, regionList.getRegions().size());
        regionList.getRegions().add(newRegion);

        assertEquals(2, regionList.getRegions().size());
        assertEquals(newRegion.toString(), regionList.getRegions().get(1).toString());
    }

    @Test
    void freeRegionsCorrect(){
        regionList.getRegions().get(0).setForkliftSerialNumber("Mirek");

        regionListService.freeRegions("Mirek");

        assertEquals("", regionList.getRegions().get(0).getForkliftSerialNumber());
    }

    /*@Test
    void getRegionsListDeepCopyTest(){
        List<Region> deepCopyRegionList = regionListService.getRegionsList();

        assertEquals(deepCopyRegionList.toString(), regionList.getRegions().toString());

        deepCopyRegionList.get(0).setForkliftSerialNumber("Test");

        assertNotEquals(deepCopyRegionList.toString(), regionList.getRegions().toString());

    }*/
}