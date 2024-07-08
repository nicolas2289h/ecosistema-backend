package com.semillero.ecosistemas.service;


import com.semillero.ecosistemas.model.Product;
import com.semillero.ecosistemas.model.Status;
import com.semillero.ecosistemas.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{
    @Autowired
    IProductRepository productRepository;

    //Create
    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    //Find
    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> findProductByName(String name) {
        return productRepository.findByNameStartingWithIgnoreCase(name);
    }

    //Read
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //Update
    @Override
    public Product editProduct(Long id, Product product) {
        Product editProduct = this.findProductById(id);
        if (editProduct != null) {
            editProduct.setName(product.getName());
            editProduct.setDescription(product.getDescription());
            editProduct.setEmail(product.getEmail());
            editProduct.setPhoneNumber(product.getPhoneNumber());
            editProduct.setFacebook(product.getFacebook());
            editProduct.setInstagram(product.getInstagram());
            editProduct.setCity(product.getCity());
            editProduct.setImagesURLs(product.getImagesURLs());
            return this.saveProduct(editProduct); //
        } else return null;
    }

    @Override
    public Product changeStatus(Long id, String status) {
        Product statusProduct = this.findProductById(id);
        if (statusProduct == null) {
            throw new IllegalArgumentException("Producto no encontrado con el ID: " + id);
        }

        try {
            Status newStatus = Status.valueOf(status.toUpperCase());
            statusProduct.setStatus(newStatus);
            return this.saveProduct(statusProduct);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El status proporcionado no es válido: " + status);
        }
    }

    @Override
    public Product sendFeedback(Long id, String feedback) {
        Product feedbackProduct = this.findProductById(id);
        if (feedbackProduct == null) {
            throw new IllegalArgumentException("Producto no encontrado con el ID: " + id);
        }
        feedbackProduct.setFeedback(feedback);
        return this.saveProduct(feedbackProduct);
    }
}