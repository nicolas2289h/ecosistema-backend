package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailTestController {

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/admin")
    public ResponseEntity<String> sendTestEmail(@RequestParam String email, @RequestParam String nombre) {
        try {
            emailSenderService.sendAdminEmail(email, nombre);
            return ResponseEntity.ok("Correo para los Admins enviado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el correo para los Admins.");
        }
    }

    @PostMapping("/supplier")
    public ResponseEntity<String> sendSupplierEmail(@RequestParam String email, @RequestParam String nombre) {
        try {
            emailSenderService.sendSupplierEmail(email, nombre);
            return ResponseEntity.ok("Correo para los Usuarios enviado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar correo para los usuarios.");
        }
    }
}