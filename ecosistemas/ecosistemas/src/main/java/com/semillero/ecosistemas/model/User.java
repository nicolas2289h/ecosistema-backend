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
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private String email;
    // private String password; --> Handled with 02AUTH
    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private Boolean deleted;
    private String rol;
    private String telephone_number;
}
