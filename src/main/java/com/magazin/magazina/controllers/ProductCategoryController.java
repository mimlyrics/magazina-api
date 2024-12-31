package com.magazin.magazina.controllers;

import com.magazin.magazina.models.ProductCategory;
import com.magazin.magazina.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/product_categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/{id}")
    public ProductCategory getProductCategoryById(@PathVariable Integer id) {
        return productCategoryService.getProductCategoryById(id);
    }

    @PostMapping
    public ProductCategory createProductCategory(@RequestBody ProductCategory productCategory) {
        System.out.println(productCategory);
        return productCategoryService.createProductCategory(productCategory);
    }

    @GetMapping
    public List <ProductCategory> getAllProductCategories() {
        return productCategoryService.getAllProductCategories();
    }

    @DeleteMapping("/{id}")
    public void deleteProductCategory(@PathVariable Integer id) {
        productCategoryService.deleteProductCategory(id);
    }

    @PutMapping("/{id}")
    public ProductCategory updateProductCategory(@PathVariable Integer id, @RequestBody ProductCategory productCategory) {
        return productCategoryService.updateProductCategory(id, productCategory);
    }


}