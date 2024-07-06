package com.semillero.ecosistemas.controller;


import com.semillero.ecosistemas.model.Product;
import com.semillero.ecosistemas.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    IProductService productService;

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        //--> HAY QUE IMPLEMENTAR UNA VALIDACION PARA SABER SI EL SUPPLIER YA TIENE 3 PRODUCTOS
        // (CUANDO IMPLEMENTEMOS SUPPLIER)
        Product newProduct = productService.saveProduct(product);
        return ResponseEntity.ok(newProduct);
    }

    @GetMapping("/findid/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id){
        Product product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/findname/{name}")
    public ResponseEntity<List<Product>> findProductByName(@PathVariable String name){
        List<Product>products = productService.findProductByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product>products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/editproduct/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable Long id, @RequestBody Product product){
        Product editProduct = productService.editProduct(id, product);
        return ResponseEntity.ok(editProduct);
    }


    @PatchMapping("/changestatus/{id}")
    public ResponseEntity<Product> changeStatus(@PathVariable Long id, @RequestBody String status) {
        Product changeStatus = productService.changeStatus(id, status);
        return ResponseEntity.ok(changeStatus);
    }


    @PatchMapping("/sendfeedback/{id}")
    public ResponseEntity<Product> sendFeedback(@PathVariable Long id, @RequestBody String feedback){
        Product sendFeedback = productService.sendFeedback(id, feedback);
        return ResponseEntity.ok(sendFeedback);
    }


}