package com.example.forkliftrestserver.controller;

import com.example.forkliftrestserver.model.Region;
import com.example.forkliftrestserver.service.ForkliftService;
import com.example.forkliftrestserver.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {

    private ForkliftService forkliftService;
    private RegionService regionService;

    @Autowired
    public RegionController(ForkliftService forkliftService, RegionService regionService) {
        this.forkliftService = forkliftService;
        this.regionService = regionService;
    }

    @GetMapping("/getRegions")
    public List<Region> getRegionList() {
        return regionService.getRegionsList();
    }

    @PostMapping("/region")
    public ResponseEntity<Region> addRegion(@RequestBody Region region) {
        regionService.addRegion(region);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
