package com.semillero.ecosistemas.service;

import com.semillero.ecosistemas.model.Product;

import java.util.List;

public interface IProductService {

    public Product saveProduct(Product product);

    public Product findProductById(Long id);

    public List<Product> findProductByName(String name);

    public List<Product> getAllProducts();

    public Product editProduct(Long id, Product product);
}
