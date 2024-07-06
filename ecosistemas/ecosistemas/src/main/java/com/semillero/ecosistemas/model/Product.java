package com.semillero.ecosistemas.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    //private Category category;
    private String email;
    private String phoneNumber;
    private String facebook;
    private String instagram;
    //private Country country;
    //private Province province;
    private String city;

    @ElementCollection
    @NotEmpty(message = "Must have at least 1 image.")
    @Size(max = 3)
    private List<String> imagesURLs;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status;

    private Boolean deleted;
    private String feedback;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
//    private Supplier supplier_id;

}