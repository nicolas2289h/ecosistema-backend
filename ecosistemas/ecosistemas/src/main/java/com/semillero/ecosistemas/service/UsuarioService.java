package com.semillero.ecosistemas.service;

import com.semillero.ecosistemas.model.Usuario;
import com.semillero.ecosistemas.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService{

    @Autowired
    IUsuarioRepository usuarioRepo;

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    @Override
    public Usuario encontrarUsuario(Long id) {
        return usuarioRepo.findById(id).orElse(null);
    }

    @Override
    public void cambiarEstado(Usuario usuario) {
        if (!usuario.getDeleted()){
            usuario.setDeleted(true); // Desactiva cuenta
        }
        else{
            usuario.setDeleted(false); // Reactiva la cuenta
        }
    }
}