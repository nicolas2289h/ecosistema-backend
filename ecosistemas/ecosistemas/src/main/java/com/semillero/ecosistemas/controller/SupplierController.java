package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.model.Supplier;
import com.semillero.ecosistemas.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Suppliers")
public class SupplierController {
    @Autowired
    ISupplierService supplierService;

    @PostMapping
    public ResponseEntity<Supplier> saveSupplier(@RequestBody Supplier supplier) {
        Supplier newSupplier = supplierService.saveSupplier(supplier);
        return ResponseEntity.ok(newSupplier);
    }

    @GetMapping
    public List<Supplier> getAllSuppliers(){
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<Supplier> getSupplierByEmail(@PathVariable String email){
        Optional<Supplier> optionalSupplier = supplierService.findSupplierByEmail(email);
        return optionalSupplier.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping("/changestate/{id}")
    public ResponseEntity<Supplier> switchSupplierState(@PathVariable Long id) {
        Optional<Supplier> supplierOptional = supplierService.findSupplierById(id);
        if (supplierOptional.isPresent()) {
            Supplier supplier = supplierOptional.get();
            supplierService.switchState(supplier);
            supplierService.saveSupplier(supplier);
            return ResponseEntity.ok(supplier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}