package com.semillero.ecosistemas.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String picture;
    private String telephoneNumber;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private Boolean deleted;

    private String role;

    @PrePersist
    public void prePersist() {
        if (deleted == null) {
            deleted = false;
        }
        if (role == null) {
            role = "USER";
        }
    }
}