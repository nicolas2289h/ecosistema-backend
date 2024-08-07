package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.dto.ProductDTO;
import com.semillero.ecosistemas.model.Product;
import com.semillero.ecosistemas.model.Supplier;
import com.semillero.ecosistemas.service.IProductService;
import com.semillero.ecosistemas.service.ICategoryService;
import com.semillero.ecosistemas.service.ICountryService;
import com.semillero.ecosistemas.service.IProvinceService;
import com.semillero.ecosistemas.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    //Dependency Injection
    @Autowired
    private IProductService productService;
    @Autowired
    private ISupplierService supplierService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ICountryService countryService;
    @Autowired
    private IProvinceService provinceService;

    //CREATE A PRODUCT (SUPPLIER)
//    @PreAuthorize("hasRole('SUPPLIER')")
    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestParam("name") String name,
            @RequestParam("shortDescription") String shortDescription,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam(required = false, name = "instagram") String instagram,
            @RequestParam(required = false, name = "facebook") String facebook,
            @RequestParam("countryId") Long countryId,
            @RequestParam("provinceId") Long provinceId,
            @RequestParam(required = false, name = "city") String city,
            @RequestParam(required = false, name = "longDescription") String longDescription,
            @RequestParam("files") List<MultipartFile> files,
            @RequestHeader("Authorization") String authorizationHeader) {

        try {
            // Extraer el token del header
            String token = authorizationHeader.replace("Bearer ", "");


            // Construir el ProductDTO
            ProductDTO productDTO = productService.buildProductDTO(
                    name,
                    shortDescription,
                    categoryId,
                    email,
                    phoneNumber,
                    instagram,
                    facebook,
                    countryId,
                    provinceId,
                    city,
                    longDescription
            );

            // Crear el producto usando el ProductDTO, archivos y token
            Product product = productService.createProduct(productDTO, files, token);

            if(product==null){
                throw new IllegalArgumentException("El Proveedor ya alcanzo el limite de 3 productos.");
            }
            else{
                return ResponseEntity.ok(product);
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    //UPDATE PRODUCT (SUPPLIER)
//    @PreAuthorize("hasRole('SUPPLIER')")
    @PutMapping("/update/{productID}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productID,
            @RequestParam("name") String name,
            @RequestParam("shortDescription") String shortDescription,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam(required = false, name = "instagram") String instagram,
            @RequestParam(required = false, name = "facebook") String facebook,
            @RequestParam("countryId") Long countryId,
            @RequestParam("provinceId") Long provinceId,
            @RequestParam(required = false, name = "city") String city,
            @RequestParam(required = false, name = "longDescription") String longDescription,
            @RequestParam(required = false, name = "URLsToDelete") List<String> URLsToDelete,
            @RequestParam(required = false, name = "files") List<MultipartFile> files) {

        try {
            // Construir el ProductDTO
            ProductDTO productDTO = productService.buildProductDTO(
                    name,
                    shortDescription,
                    categoryId,
                    email,
                    phoneNumber,
                    instagram,
                    facebook,
                    countryId,
                    provinceId,
                    city,
                    longDescription
            );

            return ResponseEntity.ok(productService.updateProduct(productID, productDTO, URLsToDelete, files));

        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    //GET SUPPLIER'S PRODUCTS (SUPPLIER)
//    @PreAuthorize("hasRole('SUPPLIER')")
    @GetMapping("/{supplierID}")
    public ResponseEntity<List<Product>> getProductsBySupplier(@PathVariable Long supplierID){
        List<Product>productsBySupplier = productService.getProductsBySupplier(supplierID);
        return ResponseEntity.ok(productsBySupplier);
    }

    //DELETE PRODUCT (SUPPLIER)
//    @PreAuthorize("hasRole('SUPPLIER')")
    @DeleteMapping("/delete/{productID}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productID) throws IOException {
        String response = productService.deleteProduct(productID);
        return ResponseEntity.ok(response);
    }

    //FIND PRODUCT BY ID (ADMIN)
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/find/{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id){
        Optional<Product> optionalProduct = productService.findProductById(id);
        return optionalProduct.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //GET ALL PRODUCTS (ADMIN)
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product>allProducts = productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    //GET PRODUCTS WITH "REVISION_INICIAL" (ADMIN)
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/revision")
    public ResponseEntity<List<Product>> getProductsToRevision(){
        List<Product>allProducts = productService.getAllProducts();
        List<Product>revisionProducts = new ArrayList<>();
        for(Product product:allProducts){
            if(product.getStatus().toString().equals("REVISION_INICIAL")&&!product.getDeleted()){
                revisionProducts.add(product);
            }
        }
        return ResponseEntity.ok(revisionProducts);
    }

    //GET PRODUCTS WITH "CAMBIOS_REALIZADOS" (ADMIN)
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/withchanges")
    public ResponseEntity<List<Product>> getProductsWithChanges(){
        List<Product>allProducts = productService.getAllProducts();
        List<Product>changedProducts = new ArrayList<>();
        for(Product product:allProducts){
            if(product.getStatus().toString().equals("CAMBIOS_REALIZADOS")&&!product.getDeleted()){
                changedProducts.add(product);
            }
        }
        return ResponseEntity.ok(changedProducts);
    }

    //SEND FEEDBACK AND SET STATUS (ADMIN)
//    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/feedback/{productID}")
    public ResponseEntity<String> sendFeedbackStatus(@PathVariable Long productID,
                                                     @RequestParam String status,
                                                     @RequestParam String feedback){
        productService.setFeedStatus(productID, status, feedback);
        return ResponseEntity.ok("Se ha actualizado el STATUS del producto y se ha enviado el feedback al Proveedor.");
    }

    //GET ALL VISIBLE PRODUCTS (ANY)
    @GetMapping
    public ResponseEntity<List<Product>> getActiveProducts(){
        List<Product>allProducts = productService.getAllProducts();
        List<Product>acceptedProducts = new ArrayList<>();
        for(Product product:allProducts){
            if(product.getStatus().toString().equals("ACEPTADO")&&!product.getDeleted()){
                acceptedProducts.add(product);
            }
        }
        return ResponseEntity.ok(acceptedProducts);
    }

    //GET PRODUCTS BY CATEGORY (ANY)
    @GetMapping("/category/{categoryID}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryID){
        List<Product>productsByCategory = productService.getProductsByCategory(categoryID);
        List<Product>acceptedProducts = new ArrayList<>();
        for(Product product:productsByCategory){
            if(product.getStatus().toString().equals("ACEPTADO")&&!product.getDeleted()){
                acceptedProducts.add(product);
            }
        }
        return ResponseEntity.ok(acceptedProducts);
    }

    //FIND PRODUCT BY NAME (SEARCHBAR - ANY)
    @GetMapping("/search/{name}")
    public ResponseEntity<List<Product>> findProductByName(@PathVariable String name){
        List<Product> allProducts = productService.findProductByName(name);
        List<Product> acceptedProducts = new ArrayList<>();
        for(Product product:allProducts){
            if(product.getStatus().toString().equals("ACEPTADO")){
                acceptedProducts.add(product);
            }
        }
        return ResponseEntity.ok(acceptedProducts);
    }
}