package com.example.forkliftrestserver.controller;

import com.example.forkliftrestserver.model.Region;
import com.example.forkliftrestserver.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ServerController {

    @Autowired
    RegionService regionService;

    @RequestMapping("/region")
    public String regionPage(Model model){

        List<Region> regionList = regionService.getRegionsList();

        model.addAttribute("regions", regionList);
        return "region";
    }
}
