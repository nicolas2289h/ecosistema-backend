package com.semillero.ecosistemas.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.semillero.ecosistemas.dto.ProductDTO;
import com.semillero.ecosistemas.model.Product;
import com.semillero.ecosistemas.model.Status;
import com.semillero.ecosistemas.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final Cloudinary cloudinary;

    @Autowired
    public ProductService(IProductRepository productRepository, Cloudinary cloudinary) {
        this.productRepository = productRepository;
        this.cloudinary = cloudinary;
    }

    // Create
    @Override
    public Product saveProduct(ProductDTO productDTO, List<MultipartFile> files) throws IOException {
        if (files == null || files.isEmpty() || files.stream().allMatch(MultipartFile::isEmpty)) {
            throw new IllegalArgumentException("Debe adjuntar al menos un archivo.");
        }
        if (files.size() > 3) {
            throw new IllegalArgumentException("No puede adjuntar más de 3 archivos.");
        }

        Set<String> imageUrls = new HashSet<>();
        for (MultipartFile file : files) {
            uploadImageProduct(file, imageUrls);
        }

        // No setear imagesURLs en el DTO, esto se maneja internamente en el servicio
        productDTO.setImagesURLs(new ArrayList<>(imageUrls));
        Product product = ProductDTO.toEntity(productDTO);
        return productRepository.save(product);
    }



    // Save a Product entity directly
    public Product saveProduct(Product product) {
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

    // Update
    @Override
    public Product editProduct(Long id, ProductDTO productDTO, List<MultipartFile> files) throws IOException {
        Product foundProduct = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el producto con id: " + id));

        if (productDTO.getUrlsToDelete() != null && !productDTO.getUrlsToDelete().isEmpty()) {
            for (String url : productDTO.getUrlsToDelete()) {
                deleteImageProduct(url);
                foundProduct.getImagesURLs().remove(url);
            }
        }

        foundProduct.setName(productDTO.getName());
        foundProduct.setShortDescription(productDTO.getShortDescription());
        foundProduct.setEmail(productDTO.getEmail());
        foundProduct.setPhoneNumber(productDTO.getPhoneNumber());
        foundProduct.setInstagram(productDTO.getInstagram());
        foundProduct.setFacebook(productDTO.getFacebook());
        foundProduct.setCity(productDTO.getCity());
        foundProduct.setLongDescription(productDTO.getLongDescription());

        if (files != null && !files.isEmpty()) {
            Set<String> existingImageUrls = foundProduct.getImagesURLs();
            Set<String> newImageUrls = new HashSet<>(existingImageUrls);
            for (MultipartFile file : files) {
                if (newImageUrls.size() >= 3) break;
                uploadImageProduct(file, newImageUrls);
            }
            foundProduct.setImagesURLs(newImageUrls);
        }

        return productRepository.save(foundProduct);
    }

    @Override
    public void switchState(Product product) {
        product.setDeleted(!product.getDeleted());
    }

    @Override
    public Product changeStatus(Long id, String status) {
        Optional<Product> optionalProduct = this.findProductById(id);

        if (optionalProduct.isPresent()) {
            Product statusProduct = optionalProduct.get();
            try {
                Status newStatus = Status.valueOf(status.toUpperCase());
                statusProduct.setStatus(newStatus);
                return productRepository.save(statusProduct);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("El status proporcionado no es válido: " + status);
            }
        } else {
            throw new IllegalArgumentException("Producto no encontrado con el ID: " + id);
        }
    }

    @Override
    public Product sendFeedback(Long id, String feedback) {
        Optional<Product> optionalProduct = this.findProductById(id);

        if (optionalProduct.isPresent()) {
            Product feedbackProduct = optionalProduct.get();
            feedbackProduct.setFeedback(feedback);
            return productRepository.save(feedbackProduct);
        } else {
            throw new IllegalArgumentException("Producto no encontrado con el ID: " + id);
        }
    }

    // Cloudinary
    private void uploadImageProduct(MultipartFile file, Set<String> imagesUrls) throws IOException {
        if (file != null && !file.isEmpty()) {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String newImageUrl = uploadResult.get("url").toString();
            imagesUrls.add(newImageUrl);
        }
    }

    @Override
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
}