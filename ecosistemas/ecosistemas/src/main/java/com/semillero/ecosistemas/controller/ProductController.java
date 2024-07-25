package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.dto.ProductDTO;
import com.semillero.ecosistemas.model.Product;
import com.semillero.ecosistemas.service.IProductService;
import com.semillero.ecosistemas.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    IProductService productService;

    @Autowired
    ISupplierService supplierService;

    @PreAuthorize("hasRole('SUPPLIER')")
    @PostMapping
    public ResponseEntity<Product> createProduct(@ModelAttribute ProductDTO productDTO,
                                                 @RequestParam("files") List<MultipartFile> files) throws IOException {
        if (files == null || files.isEmpty() || files.size() > 3) {
            return ResponseEntity.badRequest().body(null);
        }
        Product product = productService.saveProduct(productDTO, files);
        return ResponseEntity.ok(product);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPLIER', 'USER')")
    @GetMapping("/findid/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id){
        Optional<Product> optionalProduct = productService.findProductById(id);
        return optionalProduct.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPLIER', 'USER')")
    @GetMapping("/find/{name}")
    public ResponseEntity<List<Product>> findProductByName(@PathVariable String name){
        List<Product> products = productService.findProductByName(name);
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPLIER', 'USER')")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPPLIER')")
    @PutMapping("/changestate/{id}")
    public ResponseEntity<Product> switchProductState(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findProductById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            productService.switchState(product);
            return ResponseEntity.ok(productService.saveProduct(product));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('SUPPLIER')")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @ModelAttribute ProductDTO productDTO,
                                                 @RequestParam("files") List<MultipartFile> files) throws IOException {
        Product product = productService.editProduct(id, productDTO, files);
        return ResponseEntity.ok(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/changestatus/{id}")
    public ResponseEntity<Product> changeStatus(@PathVariable Long id, @RequestBody String status) {
        Product changeStatus = productService.changeStatus(id, status);
        return ResponseEntity.ok(changeStatus);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/sendfeedback/{id}")
    public ResponseEntity<Product> sendFeedback(@PathVariable Long id, @RequestBody String feedback){
        Product sendFeedback = productService.sendFeedback(id, feedback);
        return ResponseEntity.ok(sendFeedback);
    }
}
