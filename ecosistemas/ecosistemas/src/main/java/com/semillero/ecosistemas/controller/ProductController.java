package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.model.Product;
import com.semillero.ecosistemas.model.Supplier;
import com.semillero.ecosistemas.service.IProductService;
import com.semillero.ecosistemas.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    IProductService productService;

    @Autowired
    ISupplierService supplierService;

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Supplier supplier = supplierService.findSupplierById(product.getSupplier_id().getId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        if (supplier.getProductList().size() < 3) {
            supplier.getProductList().add(product);
            product.setSupplier_id(supplier);

            Product newProduct = productService.saveProduct(product);
            supplierService.saveSupplier(supplier); // Guardar cambios en el Supplier
            return ResponseEntity.ok(newProduct);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/findid/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id){
        Optional<Product> optionalProduct = productService.findProductById(id);

        return optionalProduct.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
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

    @PutMapping("/changestate/{id}")
    public ResponseEntity<Product> switchProductState(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findProductById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            productService.switchState(product);
            productService.saveProduct(product);
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
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