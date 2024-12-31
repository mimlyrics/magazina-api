package com.magazin.magazina.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.List;

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
    private String imageUrl;

    public String getName() {
        return name;
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id", columnDefinition = "INTEGER")
    @JsonBackReference("category-productCategory")
    private Category category;

    @OneToMany(mappedBy = "productCategory")
    @JsonManagedReference("productCategory-product")
    private List <Product> products;

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
