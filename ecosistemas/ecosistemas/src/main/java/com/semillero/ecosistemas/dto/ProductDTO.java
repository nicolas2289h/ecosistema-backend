package com.semillero.ecosistemas.dto;

import com.semillero.ecosistemas.model.Product;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;

    @NotBlank(message = "El campo nombre es obligatorio.")
    private String name;

    @NotBlank(message = "El campo descripción breve es obligatorio.")
    @Length(max = 50, message = "La cantidad máxima de caracteres es 50.")
    private String shortDescription;

    @NotBlank(message = "El campo correo electrónico es obligatorio.")
    private String email;

    @NotBlank(message = "El campo teléfono/whatsapp es obligatorio.")
    private String phoneNumber;

    private String instagram;
    private String facebook;

    private String city;

    @NotBlank(message = "El campo descripción completa es obligatorio.")
    @Length(max = 300, message = "La cantidad máxima de caracteres es 300.")
    private String longDescription;

    // Se eliminan las validaciones de imágenes
    private List<String> imagesURLs = new ArrayList<>();

    private List<String> urlsToDelete;

    public static Product toEntity(ProductDTO productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .shortDescription(productDto.getShortDescription())
                .email(productDto.getEmail())
                .phoneNumber(productDto.getPhoneNumber())
                .instagram(productDto.getInstagram())
                .facebook(productDto.getFacebook())
                .city(productDto.getCity())
                .longDescription(productDto.getLongDescription())
                .build();
    }

    public static ProductDTO fromEntity(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .shortDescription(product.getShortDescription())
                .email(product.getEmail())
                .phoneNumber(product.getPhoneNumber())
                .instagram(product.getInstagram())
                .facebook(product.getFacebook())
                .city(product.getCity())
                .longDescription(product.getLongDescription())
                .imagesURLs(new ArrayList<>(product.getImagesURLs()))
                .build();
    }
}