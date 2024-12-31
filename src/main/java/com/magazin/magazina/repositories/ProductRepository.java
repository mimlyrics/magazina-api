package com.magazin.magazina.repositories;


import com.magazin.magazina.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product, Integer> {

}
