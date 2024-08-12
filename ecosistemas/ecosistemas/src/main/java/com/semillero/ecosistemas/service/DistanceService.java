package com.semillero.ecosistemas.service;

import com.semillero.ecosistemas.dto.DistanceResult;
import com.semillero.ecosistemas.dto.ProductGeoDto;
import com.semillero.ecosistemas.model.Coordinates;
import com.semillero.ecosistemas.model.Country;
import com.semillero.ecosistemas.model.Product;
import com.semillero.ecosistemas.model.Province;
import com.semillero.ecosistemas.repository.IProductRepository;
import com.semillero.ecosistemas.utils.DistanceCalculator;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class DistanceService {

    private final IProductRepository productRepository;
    private final DistanceCalculator distanceCalculator;
    private final GeocodingService geocodingService;

    public DistanceService(IProductRepository productRepository, DistanceCalculator distanceCalculator, GeocodingService geocodingService) {
        this.productRepository = productRepository;
        this.distanceCalculator = distanceCalculator;
        this.geocodingService = geocodingService;
    }

    public List<DistanceResult> calculateTop5ShortestDistances(double lat, double lon) {
        List<Product> products = productRepository.findAll();

        List<DistanceResult> distanceResults = new ArrayList<>();

        for (Product product : products) {
            Province province = product.getProvince();
            Country country = product.getCountry();

            if (province == null || country == null) continue;

            String normalizedProvinceName = Normalizer.normalize(province.getName(), Normalizer.Form.NFD);
            normalizedProvinceName = normalizedProvinceName.replaceAll("\\p{M}", "");
            String formattedProvinceName = normalizedProvinceName.replace(" ", "+");

            String normalizedCountryName = Normalizer.normalize(country.getName(), Normalizer.Form.NFD);
            normalizedCountryName = normalizedCountryName.replaceAll("\\p{M}", "");
            String formattedCountryName = normalizedCountryName.replace(" ", "+");

            String combinedLocationName = formattedProvinceName + "," + formattedCountryName;

            Coordinates coordinates = geocodingService.getCoordinatesByName(combinedLocationName);
            double targetLat = Double.parseDouble(coordinates.getLatitude());
            double targetLon = Double.parseDouble(coordinates.getLongitude());
            double distance = distanceCalculator.calculate(lat, lon, targetLat, targetLon);

            ProductGeoDto productGeoDto = new ProductGeoDto(product);

            distanceResults.add(new DistanceResult(productGeoDto, distance));
        }

        Collections.sort(distanceResults, (dr1, dr2) -> Double.compare(dr1.getDistance(), dr2.getDistance()));

        return distanceResults.size() > 5 ? distanceResults.subList(0, 5) : distanceResults;
    }
}
