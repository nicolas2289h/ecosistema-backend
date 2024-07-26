package com.semillero.ecosistemas.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.semillero.ecosistemas.dto.ProductDTO;
import com.semillero.ecosistemas.jwt.JwtService;
import com.semillero.ecosistemas.model.*;
import com.semillero.ecosistemas.repository.IProductRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ISupplierService supplierService;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ICountryService countryService;

    @Autowired
    private IProvinceService provinceService;

    // Método para construir el ProductDTO
    @Override
    public ProductDTO buildProductDTO(
            String name,
            String shortDescription,
            Long categoryId,
            String email,
            String phoneNumber,
            String instagram,
            String facebook,
            Long countryId,
            Long provinceId,
            String city,
            String longDescription) {

        Category category = categoryService.findCategoryById(categoryId);
        Country country = countryService.getCountryById(countryId);
        Province province = provinceService.getProvinceById(provinceId);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setShortDescription(shortDescription);
        productDTO.setCategory(category);
        productDTO.setEmail(email);
        productDTO.setPhoneNumber(phoneNumber);
        productDTO.setInstagram(instagram);
        productDTO.setFacebook(facebook);
        productDTO.setCountry(country);
        productDTO.setProvince(province);
        productDTO.setCity(city);
        productDTO.setLongDescription(longDescription);

        return productDTO;
    }

    @Override
    public Product createProduct(ProductDTO productDTO, List<MultipartFile> files, String token) throws IOException {
        List<String> productImages = new ArrayList<>();
        for (MultipartFile file : files) {
            String imageURL = uploadImage(file);
            productImages.add(imageURL);
        }

        Long supplierId = extractSupplierIdFromToken(token);
        Supplier supplier = supplierService.findSupplierById(supplierId)
                .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));

        Product product = Product.builder()
                .name(productDTO.getName())
                .shortDescription(productDTO.getShortDescription())
                .category(productDTO.getCategory())
                .email(productDTO.getEmail())
                .phoneNumber(productDTO.getPhoneNumber())
                .instagram(productDTO.getInstagram())
                .facebook(productDTO.getFacebook())
                .country(productDTO.getCountry())
                .province(productDTO.getProvince())
                .city(productDTO.getCity())
                .longDescription(productDTO.getLongDescription())
                .imagesURLs(productImages)
                .supplier(supplier)
                .build();

        return productRepository.save(product);
    }

    // Find
    @Override
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findProductByName(String name) {
        return productRepository.findByNameStartingWithIgnoreCase(name);
    }

    // Read
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsBySupplier(Long id) {
        Optional<Supplier> supplierOptional = supplierService.findSupplierById(id);
        if(supplierOptional.isPresent()){
            Supplier supplier = supplierOptional.get();
            return supplier.getProductList();
        }
        return null;
    }

    @Override
    public List<Product> getProductsByCategory(Long id) {
        Category category = categoryService.findCategoryById(id);
        List<Product> allProducts = this.getAllProducts();
        List<Product> categoryProducts = new ArrayList<>();
        for(Product product : allProducts){
            if(product.getCategory().equals(category)){
                categoryProducts.add(product);
            }
        }
        return categoryProducts;
    }



    @Override
    public void setFeedStatus(Long id, String status, String feedback) {
        Optional<Product> optionalProduct = this.findProductById(id);

        if (optionalProduct.isPresent()) {
            Product toFeedbackProduct = optionalProduct.get();
            try {
                Status newStatus = Status.valueOf(status.toUpperCase());
                toFeedbackProduct.setStatus(newStatus);
                toFeedbackProduct.setFeedback(feedback);
                productRepository.save(toFeedbackProduct);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("El status proporcionado no es válido: " + status);
            }
        } else {
            throw new IllegalArgumentException("Producto no encontrado con el ID: " + id);
        }
    }

    /*
    @Override
    public Product updateProduct(Long id, ProductDTO productDTO, List<MultipartFile> files) throws IOException {
        //Traer el producto original
        Product previousProduct = productRepository.findById(id).orElse(null);

        //Eliminar las imagenes que ya no son necesarias (Llegan del Front con el DTO)
        if(productDTO.getUrlsToDelete()!=null){
            for(String url:productDTO.getUrlsToDelete()){
                this.deleteImageProduct(url);
            }
        }



        return null;
    }

     */

    @Override
    public void deleteProduct(Long id) throws IOException {
        Product product = productRepository.findById(id).orElse(null);
        List<String>imagesToDelete = product.getImagesURLs();
        for(String url:imagesToDelete){
            this.deleteImageProduct(url);
        }
        productRepository.deleteById(id);
    }

    //Aux Methods
    private String uploadImage(MultipartFile file) throws IOException {
        Map<String, String> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url");
    }

    public void deleteImageProduct(String url) throws IOException {
        String publicId = extractPublicIdFromUrl(url);
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new IOException("Error al eliminar la imagen.");
        }
    }

    private String extractPublicIdFromUrl(String url) {
        int startIndex = url.lastIndexOf("/") + 1;
        int endIndex = url.lastIndexOf('.');
        return url.substring(startIndex, endIndex);
    }

    private Long extractSupplierIdFromToken(String token) {
        Claims claims = jwtService.extractClaims(token);
        return claims.get("id", Long.class);
    }
}