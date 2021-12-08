package com.example.forkliftrestserver;

import com.example.forkliftrestserver.controller.ForkliftController;
import com.example.forkliftrestserver.model.Forklift;
import com.example.forkliftrestserver.model.Region;
import com.example.forkliftrestserver.model.RegionForklift;
import com.example.forkliftrestserver.service.ForkliftService;
import com.example.forkliftrestserver.service.RegionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class ForkliftRestServerApplicationTests {
    RegionService regionService = new RegionService();
    ForkliftController forkliftController = new ForkliftController(new ForkliftService(), regionService);

    @Test
    public void registrationOutsideRegion() {
        RegionForklift regionForklift = new RegionForklift();
        Forklift forklift = new Forklift("Mirek", new Point(10, 10));
        regionForklift.setForklift(forklift);
        regionForklift.setRegion(null);
        assertEquals(new ResponseEntity<>(HttpStatus.CREATED), forkliftController.addForklift(regionForklift));
        forklift.setCoords(new Point(20, 20));
        regionForklift.setForklift(forklift);
        assertEquals(new ResponseEntity<>(HttpStatus.CREATED), forkliftController.addForklift(regionForklift));

        assertEquals(forkliftController.getForklift("Mirek"), forklift);
        forklift.setSerialNumber(null);
        regionForklift.setForklift(forklift);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), forkliftController.addForklift(regionForklift));

        forklift.setCoords(null);
        regionForklift.setForklift(forklift);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), forkliftController.addForklift(regionForklift));

        regionForklift.setForklift(null);
        assertEquals(new ResponseEntity<>(HttpStatus.BAD_REQUEST), forkliftController.addForklift(regionForklift));
    }

    @Test
    public void registrationInsideRegion() {
        RegionForklift regionForklift = new RegionForklift();
        Forklift forklift1 = new Forklift("Mirek", new Point(53, 53));
        regionForklift.setForklift(forklift1);
        Region region = regionService.getRegionsList().get(0);
        regionForklift.setRegion(region);
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), forkliftController.addForklift(regionForklift));
        region = regionService.getRegionsList().get(1);
        regionForklift.setRegion(region);
        assertEquals(new ResponseEntity<>(HttpStatus.CREATED), forkliftController.addForklift(regionForklift));

        Forklift forklift2 = new Forklift("Tymek", new Point(52, 52));
        regionForklift.setForklift(forklift2);
        assertEquals(new ResponseEntity<>(HttpStatus.FORBIDDEN), forkliftController.addForklift(regionForklift));

        int[] xpoints = {200, 200, 210, 210};
        int[] ypoints = {200, 210, 210, 200};
        region.setPolygon(new Polygon(xpoints, ypoints, 4));
        regionForklift.setRegion(region);
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), forkliftController.addForklift(regionForklift));

        region = regionService.getRegionsList().get(0);
        region.setId(2);
        regionForklift.setRegion(region);
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_FOUND), forkliftController.addForklift(regionForklift));
    }

}
