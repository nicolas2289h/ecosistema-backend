package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.dto.DashboardDTO;
import com.semillero.ecosistemas.model.Publication;
import com.semillero.ecosistemas.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboardInfo(){
        Map<String, Object> dashboardInfo = dashboardService.getDashboardInfo();
        return new ResponseEntity<>(dashboardInfo, HttpStatus.OK);
    }

    @GetMapping("/suppliers")
    public ResponseEntity<Map<String, Long>> getSupplierCountByCategory(){
        Map<String, Long> supplierCountByCategory = dashboardService.getSupplierCountByCategory();
        return new ResponseEntity<>(supplierCountByCategory, HttpStatus.OK);
    }

}
