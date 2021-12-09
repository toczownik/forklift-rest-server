package com.example.forkliftrestserver.controller;

import com.example.forkliftrestserver.model.Region;
import com.example.forkliftrestserver.service.ForkliftService;
import com.example.forkliftrestserver.service.RegionListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {

    private ForkliftService forkliftService;
    private RegionListService regionListService;

    @Autowired
    public RegionController(ForkliftService forkliftService, RegionListService regionListService) {
        this.forkliftService = forkliftService;
        this.regionListService = regionListService;
    }

    @GetMapping("/getRegions")
    public List<Region> getRegionList() {
        return regionListService.getRegionsList();
    }

    @PostMapping("/region")
    public ResponseEntity<Region> addRegion(@RequestBody Region region) {
        regionListService.addRegion(region);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
