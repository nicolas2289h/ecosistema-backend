package com.semillero.ecosistemas.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="suppliers")
public class Supplier extends User{
    @OneToMany(mappedBy = "supplier_id", cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(max = 3)
    @JsonManagedReference
    List<Product> productList;

    @PrePersist
    @Override
    public void prePersist() {
        super.prePersist();
        setRole(Role.SUPPLIER);
        setDeleted(false);
        if (productList == null) {
            productList = new ArrayList<>();
        }
    }
}