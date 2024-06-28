package com.semillero.ecosistemas.controller;

import com.semillero.ecosistemas.model.Usuario;
import com.semillero.ecosistemas.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    IUsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario user) {
        Usuario newUser = usuarioService.guardarUsuario(user);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> cambiarEstado(@PathVariable Long id) {

        Usuario user = usuarioService.encontrarUsuario(id);
        if(user!=null){
            usuarioService.cambiarEstado(user);
            usuarioService.guardarUsuario(user);
            return ResponseEntity.ok(user);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}