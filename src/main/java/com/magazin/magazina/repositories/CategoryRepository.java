package com.magazin.magazina.repositories;

import com.magazin.magazina.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository <Category, Integer> {
}
