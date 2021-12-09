package com.example.forkliftrestserver.controller;

import com.example.forkliftrestserver.model.*;
import com.example.forkliftrestserver.service.ForkliftService;
import com.example.forkliftrestserver.service.RegionListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.Map;

@RestController
@EnableScheduling
@RequestMapping("/forklift")
public class ForkliftController {

    private ForkliftService forkliftService;
    private RegionListService regionListService;

    @Autowired
    public ForkliftController(ForkliftService forkliftService, RegionListService regionListService) {
        this.forkliftService = forkliftService;
        this.regionListService = regionListService;
    }

    @GetMapping("/all")
    public Map<String, Forklift> getForklifts() {
        return forkliftService.getForklifts();
    }

    @GetMapping("/{serialNumber}")
    public Forklift getForklift(@PathVariable("serialNumber") String serialNumber) {
        return forkliftService.getForkliftBySerialNumber(serialNumber);
    }

    @PostMapping("/update")
    public ResponseEntity<Forklift> updateForklift(@RequestBody String serialNumber) {
        forkliftService.updateForklift(serialNumber);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/add")
    synchronized public ResponseEntity<Forklift> addForklift(@RequestBody RegionForklift regionForklift) {
        if (regionForklift.getForklift() == null || regionForklift.getForklift().getSerialNumber() == null ||
                regionForklift.getForklift().getCoords() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (regionForklift.getRegion() != null) {
            PermissionMessage permission = regionListService.getPermission(
                    regionForklift.getForklift(), regionForklift.getRegion());
            switch (permission.getStatus()) {
                case SUCCESS:
                    break;
                case OCCUPIED:
                    forkliftService.addForklift(regionForklift.getForklift(), ForkliftState.WAITING);
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                case LACK:
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                default:
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        forkliftService.addForklift(regionForklift.getForklift(), ForkliftState.ACTIVE);
        return new ResponseEntity<>(HttpStatus.CREATED);

//        aby oddzielić w 100% cześc biznesową od logiki i rest to może przekazywac do addForkift status z permission
// i tam już zrobić switcha który będzie dodawał wózek z odpowiednim stanem
    }

    @PostMapping("/getPermission")
    synchronized public ResponseEntity<String> getPermissionToRegion(@RequestBody RegionForklift regionForklift) {
        if (regionForklift.getForklift() == null || regionForklift.getForklift().getSerialNumber() == null ||
                regionForklift.getForklift().getCoords() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        PermissionMessage permission = regionListService.getPermission(regionForklift.getForklift(), regionForklift.getRegion());

        if (permission.getStatus() == RegionState.SUCCESS) {
            forkliftService.updateForklift(regionForklift.getForklift().getSerialNumber());
            return new ResponseEntity<>(HttpStatus.OK);
        } else if (permission.getStatus() == RegionState.OCCUPIED) {
            return new ResponseEntity<>(permission.getForkliftSerialNumber(), HttpStatus.FORBIDDEN);
        } else if (permission.getStatus() == RegionState.LACK) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/leaveTheRegion")
    synchronized public ResponseEntity<Forklift> leaveTheRegion(@RequestBody RegionForklift regionForklift) {
        return regionListService.leaveRegionByForklift(regionForklift.getForklift(), regionForklift.getRegion());
    }

    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<Forklift> deleteForklift(@PathVariable("serialNumber") String serialNumber) {
        if (forkliftService.forkliftExists(serialNumber)) {
            regionListService.freeRegions(serialNumber);
            forkliftService.removeForklift(serialNumber);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void checker() {
        for (Forklift forklift: forkliftService.getForklifts().values()) {
            long timeDiff = new Date().getTime() - forklift.getLastConnection().getTime();
            if (timeDiff >= 5000) {
                if (forklift.getState() == ForkliftState.INACTIVE && timeDiff > 10000) {
                    if (forklift.getTakenRegionsList().isEmpty()) {
                        forkliftService.removeForklift(forklift.getSerialNumber());
                    } else if (timeDiff > 30000) {
                        regionListService.freeRegions(forklift.getSerialNumber());
                        forkliftService.removeForklift(forklift.getSerialNumber()); // czy usuwamy od razu cały wózek z listy czy ustawiamy jako nieaktywny ?
                        // zgodnie z założeniami mamy ustawić go na nieaktywny i zwolnić region
                    }
                }
                forklift.setState(ForkliftState.INACTIVE);
            }
        }
    }
}
