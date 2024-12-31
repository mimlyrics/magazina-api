package com.magazin.magazina.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="product_categories")
public class ProductCategory {

    @Id
    @SequenceGenerator(name="productCategory_id_sequence", sequenceName = "productCategory_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productCategory_id_sequence")
    private Integer id;
    private String name;

    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public String getName() {
        return name;
    }

    //@OneToMany(mappedBy = "category_id")
    //private List<Product> products;

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Integer getId() {
        return id;
    }
}
