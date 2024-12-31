package com.magazin.magazina.services;

import com.magazin.magazina.models.ProductCategory;
import com.magazin.magazina.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public ProductCategory getProductCategoryById(Integer id) {
        return productCategoryRepository.findById(id).orElseThrow();
    }

    public ProductCategory createProductCategory(@RequestBody ProductCategory productCategoryRequest) {
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setName(productCategoryRequest.getName());
        return productCategoryRepository.save(productCategory1);
    }

    public void deleteProductCategory(Integer id) {
        productCategoryRepository.deleteById(id);
    }

    public ProductCategory updateProductCategory(Integer id, ProductCategory productCategory) {
        ProductCategory productCategory1 = productCategoryRepository.findById(id).orElseThrow();
        productCategory1.setName(productCategory.getName());
        return productCategoryRepository.save(productCategory1);
    }
}
