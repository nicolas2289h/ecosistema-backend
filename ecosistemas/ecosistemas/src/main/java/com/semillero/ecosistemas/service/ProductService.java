package com.semillero.ecosistemas.service;

import com.semillero.ecosistemas.model.Product;
import com.semillero.ecosistemas.model.Status;
import com.semillero.ecosistemas.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
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
    public void switchState(Product product){
        if (!product.getDeleted()){
            product.setDeleted(true); // Set deleted TRUE --> Product deactivation
        }
        else{
            product.setDeleted(false); // Set deleted FALSE --> Product Reactivation
        }
    }

    @Override
    public Product editProduct(Long id, Product product) {
        Optional<Product> productOptional = this.findProductById(id);

        if (productOptional.isPresent()) {
            Product editProduct = productOptional.get();

            if(product.getName()!=null){
                editProduct.setName(product.getName());
            }

            if(product.getShortDescription()!=null){
                editProduct.setShortDescription(product.getShortDescription());
            }

            if(product.getCategory()!=null){
                editProduct.setCategory(product.getCategory());
            }

            if(product.getEmail()!=null){
                editProduct.setEmail(product.getEmail());
            }

            if(product.getPhoneNumber()!=null){
                editProduct.setPhoneNumber(product.getPhoneNumber());
            }

            if(product.getInstagram()!=null){
                editProduct.setInstagram(product.getInstagram());
            }

            if(product.getFacebook()!=null){
                editProduct.setFacebook(product.getFacebook());
            }

            if(product.getCountry()!=null){
                editProduct.setCountry(product.getCountry());
            }

            if(product.getProvince()!=null){
                editProduct.setProvince(product.getProvince());
            }

            if(product.getCity()!=null){
                editProduct.setCity(product.getCity());
            }

            if(product.getLongDescription()!=null){
                editProduct.setLongDescription(product.getLongDescription());
            }

            if(product.getImagesURLs()!=null){
                editProduct.setImagesURLs(product.getImagesURLs());
            }

            return this.saveProduct(editProduct);
        }
        else{
            throw new IllegalArgumentException("Producto no encontrado con el ID: " + id);
        }
    }


    @Override
    public Product changeStatus(Long id, String status) {
        Optional<Product> optionalProduct = this.findProductById(id);

        if(optionalProduct.isPresent()){
            Product statusProduct = optionalProduct.get();
            try {
                Status newStatus = Status.valueOf(status.toUpperCase());
                statusProduct.setStatus(newStatus);
                return this.saveProduct(statusProduct);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("El status proporcionado no es válido: " + status);
            }
        }
        else{
            throw new IllegalArgumentException("Producto no encontrado con el ID: " + id);
        }
    }

    @Override
    public Product sendFeedback(Long id, String feedback) {
        Optional<Product> optionalProduct = this.findProductById(id);

        if(optionalProduct.isPresent()){
            Product feedbackProduct = optionalProduct.get();
            feedbackProduct.setFeedback(feedback);
            return this.saveProduct(feedbackProduct);
        }
        else {
            throw new IllegalArgumentException("Producto no encontrado con el ID: " + id);
        }
    }
}