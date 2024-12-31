package com.magazin.magazina.repositories;

import com.magazin.magazina.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository <ProductCategory, Integer> {


}