package com.semillero.ecosistemas.service;

import com.semillero.ecosistemas.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    //Create
    public Product saveProduct(Product product);

    //Find
    public Optional<Product> findProductById(Long id);
    public List<Product> findProductByName(String name);

    //Read
    public List<Product> getAllProducts();

    //Update
    public void switchState(Product product);
    public Product editProduct(Long id, Product product);
    public Product changeStatus(Long id, String status);
    public Product sendFeedback(Long id, String feedback);
}