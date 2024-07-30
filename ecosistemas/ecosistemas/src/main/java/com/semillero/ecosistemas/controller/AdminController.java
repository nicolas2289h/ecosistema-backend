package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.model.Admin;
import com.semillero.ecosistemas.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    @Autowired
    IAdminService adminService;

    //SAVE AN ADMIN - MODIFICAR POR SUPPLIERDTO CUANDO TENGAMOS LO QUE MANDA EL FRONT
    @PostMapping
    public ResponseEntity<Admin> saveAdmin(@RequestBody Admin admin) {
        Admin newAdmin = adminService.saveAdmin(admin);
        return ResponseEntity.ok(newAdmin);
    }

    //RETURN ALL ADMINS
    @GetMapping
    public List<Admin> getAllAdmins(){
        return adminService.getAllAdmins();
    }

    //FIND AN ADMIN BY EMAIL
    @GetMapping("/find/{email}")
    public ResponseEntity<Admin> getAdminByEmail(@PathVariable String email){
        Optional<Admin> optionalAdmin = adminService.findAdminByEmail(email);
        return optionalAdmin.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    //DEACTIVATE AN ADMIN ACCOUNT
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<Admin> switchAdminState(@PathVariable Long id) {
        Optional<Admin> adminOptional = adminService.findAdminById(id);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            adminService.deactivateAdmin(admin);
            adminService.saveAdmin(admin);
            return ResponseEntity.ok(admin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}