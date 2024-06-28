package com.semillero.ecosistemas.service;


import com.semillero.ecosistemas.model.Usuario;

public interface IUsuarioService {
    //Create
    public Usuario guardarUsuario(Usuario usuario);

    //Find
    public Usuario encontrarUsuario(Long id);

    //Update (Desactivar)
    public void cambiarEstado(Usuario usuario);
}
