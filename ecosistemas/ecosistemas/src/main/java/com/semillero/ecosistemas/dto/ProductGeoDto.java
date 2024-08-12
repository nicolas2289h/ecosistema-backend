package com.semillero.ecosistemas.dto;

import com.semillero.ecosistemas.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGeoDto {
    private Long id;
    private String name;
    private String shortDescription;
    private String province;
    private String country;
    private SupplierDto supplier;

    public ProductGeoDto(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.shortDescription = product.getShortDescription();
        this.province = product.getProvince().getName();
        this.country = product.getCountry().getName();
        if (product.getSupplier() != null) {
            this.supplier = new SupplierDto(product.getSupplier().getName(),product.getSupplier().getLastName(),product.getSupplier().getEmail(), product.getSupplier().getPicture());
        }
    }
}
