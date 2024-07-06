package com.semillero.ecosistemas.service;


import com.semillero.ecosistemas.model.Product;
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
        List<Product> products = this.getAllProducts();
        List<Product> searcher = null;
        if(products!=null){
            for(Product product : products){
                if(product.getName().equals(name)||product.getName().contains(name)){
                    searcher.add(product);
                }
            }
        }
        return searcher;
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
        if(editProduct!=null){
            editProduct = product;
            return editProduct;
        }
        else{
            return null;
        }
    }
}
