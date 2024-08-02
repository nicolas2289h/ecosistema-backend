package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.model.Province;
import com.semillero.ecosistemas.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

    @Autowired
    private IProvinceService provinceService;

    @GetMapping("/{provinceID}")
    public Province getProvinceById(@PathVariable Long provinceID){
        return provinceService.getProvinceById(provinceID);
    }

    @GetMapping("/country/{countryID}")
    public List<Province> getProvincesByCountryId(@PathVariable Long countryID) {
        return provinceService.getProvincesByCountryId(countryID);
    }
}
