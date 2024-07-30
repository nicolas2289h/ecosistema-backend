package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.model.Supplier;
import com.semillero.ecosistemas.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {
    @Autowired
    ISupplierService supplierService;

    //SAVE A SUPPLIER (REGISTRY ENDPOINT) - MODIFICAR POR SUPPLIERDTO CUANDO TENGAMOS LO QUE MANDA EL FRONT
    @PostMapping
    public ResponseEntity<Supplier> saveSupplier(@RequestBody Supplier supplier) {
        Supplier newSupplier = supplierService.saveSupplier(supplier);
        return ResponseEntity.ok(newSupplier);
    }

    //RETURN ALL SUPPLIERS
    @GetMapping
    public List<Supplier> getAllSuppliers(){
        return supplierService.getAllSuppliers();
    }

    //FIND A SUPPLIER BY EMAIL
    @GetMapping("/find/{email}")
    public ResponseEntity<Supplier> getSupplierByEmail(@PathVariable String email){
        Optional<Supplier> optionalSupplier = supplierService.findSupplierByEmail(email);
        return optionalSupplier.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    //DEACTIVATE A SUPPLIER ACCOUNT
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Supplier> deactivateSupplier(@PathVariable Long id) {
        Optional<Supplier> supplierOptional = supplierService.findSupplierById(id);
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            supplierService.deactivateSupplier(supplier);
            supplierService.saveSupplier(supplier);
            return ResponseEntity.ok(supplier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}