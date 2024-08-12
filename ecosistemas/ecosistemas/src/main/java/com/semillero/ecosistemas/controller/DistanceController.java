package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.dto.DistanceResult;
import com.semillero.ecosistemas.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/distance")
public class DistanceController {

    @Autowired
    RestTemplate restTemplate;
    private final DistanceService distanceService;

    public DistanceController(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    @GetMapping("/calculate")
    public List<DistanceResult> calculateTop5ShortestDistances(@RequestParam double lat, @RequestParam double lon) {
        return distanceService.calculateTop5ShortestDistances(lat, lon);
    }
}



