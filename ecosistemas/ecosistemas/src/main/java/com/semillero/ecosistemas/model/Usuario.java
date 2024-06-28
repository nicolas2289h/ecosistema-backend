package com.semillero.ecosistemas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    // private String password; --> SE MANEJADA DESDE 02AUTH
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private Boolean deleted;
    private String rol;
    private String telefono;
}
