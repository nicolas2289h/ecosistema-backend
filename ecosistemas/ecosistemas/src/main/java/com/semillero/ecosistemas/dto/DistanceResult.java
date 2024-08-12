package com.semillero.ecosistemas.dto;

import com.semillero.ecosistemas.model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistanceResult {
    private ProductGeoDto productGeoDto;
    private double distance;

    public DistanceResult(ProductGeoDto productGeoDto, double distance) {
        this.productGeoDto = productGeoDto;
        this.distance = distance;
    }
}
