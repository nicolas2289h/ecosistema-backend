package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailTestController {

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/admin")
    public ResponseEntity<String> sendTestEmail(@RequestParam String email) {
        try {
            emailSenderService.sendAdminEmail(email);
            return ResponseEntity.ok("Correo de prueba enviado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar correo.");
        }
    }

    @PostMapping("/supplier")
    public ResponseEntity<String> sendSupplierEmail(@RequestParam String email) {
        try {
            emailSenderService.sendSupplierEmail(email);
            return ResponseEntity.ok("Correo de prueba enviado al proveedor.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar correo al proveedor.");
        }
    }
}
