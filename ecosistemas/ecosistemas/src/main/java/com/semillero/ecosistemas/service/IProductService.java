package com.semillero.ecosistemas.service;

import com.semillero.ecosistemas.dto.ProductDTO;
import com.semillero.ecosistemas.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

    public interface IProductService {
        // Create
        public ProductDTO buildProductDTO(String name,
                                          String shortDescription,
                                          Long categoryId,
                                          String email,
                                          String phoneNumber,
                                          String instagram,
                                          String facebook,
                                          Long countryId,
                                          Long provinceId,
                                          String city,
                                          String longDescription);
        
        public Product createProduct(ProductDTO productDTO, List<MultipartFile> files, String token) throws IOException;
    }

    /*
    // Create
    Product saveProduct(ProductDTO productDTO, List<MultipartFile> files) throws IOException;
    Product saveProduct(Product product);

    // Find
    Optional<Product> findProductById(Long id);
    List<Product> findProductByName(String name);

    // Read
    List<Product> getAllProducts();

    // Update
    Product editProduct(Long id, ProductDTO productDTO, List<MultipartFile> files) throws IOException;
    void switchState(Product product);
    Product changeStatus(Long id, String status);
    Product sendFeedback(Long id, String feedback);

    // Cloudinary
    void deleteImageProduct(String url) throws IOException;

     */

