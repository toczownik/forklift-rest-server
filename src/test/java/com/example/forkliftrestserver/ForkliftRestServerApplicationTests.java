package com.example.forkliftrestserver;

import com.example.forkliftrestserver.controller.ForkliftController;
import com.example.forkliftrestserver.model.Forklift;
import com.example.forkliftrestserver.service.ForkliftService;
import com.example.forkliftrestserver.service.RegionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class ForkliftRestServerApplicationTests {

        @Test
        void updateTests() {
            ForkliftController controller = new ForkliftController(new ForkliftService(), new RegionService());
            Forklift forklift = new Forklift(30, new Point(50, 50));
            ResponseEntity<Forklift> response = controller.postForklift(forklift);
            assertEquals(new ResponseEntity<>(HttpStatus.CREATED), response);

            Map<Integer, Forklift> forkliftMap = controller.getForklifts();
            Map<Integer, Forklift> testMap = new HashMap<>();
            testMap.put(forklift.getSerialNumber(), forklift);
            assertEquals(forkliftMap, testMap);

            Forklift forklift1 = new Forklift(1, new Point(102, 102));
            response = controller.postForkliftAndCheckSemaphores(forklift1);
            assertEquals(new ResponseEntity<>(HttpStatus.OK), response);
        }

}
