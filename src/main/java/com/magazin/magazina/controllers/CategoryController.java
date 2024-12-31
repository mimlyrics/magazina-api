package com.magazin.magazina.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magazin.magazina.models.Category;
import com.magazin.magazina.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    private static final String UPLOAD_DIR = "/upload";
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Category> createCategory(
            @RequestParam("name") String name,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        System.out.println("Received name: " + name);
        System.out.println("Received file: " + file);

        // If file is provided, handle file upload
        if (file != null && !file.isEmpty()) {
            try {
                // Define the directory to store the uploaded file
                String directoryPath = UPLOAD_DIR + "/categories/" + name;
                File directory = new File(directoryPath);

                // Create parent directories if they do not exist
                if (!directory.exists() && !directory.mkdirs()) {
                    System.out.println("Failed to create directory: " + directoryPath);
                    return ResponseEntity.status(500).body(null);  // Return error if directory creation fails
                }

                // Sanitize the file name to avoid potential issues
                String sanitizedFileName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9.-]", "_");
                String filePath = directoryPath + "/" + sanitizedFileName;

                // Save the file to the server
                File targetFile = new File(filePath);
                file.transferTo(targetFile);

                // Create a new Category object and set properties
                Category category = new Category();
                category.setName(name);
                category.setCreatedAt(LocalDateTime.now());
                category.setUpdatedAt(LocalDateTime.now());
                category.setImageUrl(filePath);  // Set the file path (or URL)

                // Save the category in the database (assuming a categoryRepository is available)
                // categoryRepository.save(category);

                // Return the created category in the response
                return ResponseEntity.ok(category);

            } catch (IOException e) {
                // Handle file upload failure
                System.out.println("Error uploading file: " + e.getMessage());
                return ResponseEntity.status(500).body(null);  // Return 500 server error
            }
        } else {
            // Handle case when no file is uploaded
            System.out.println("No file uploaded.");
            // Create category without a file
            Category category = new Category();
            category.setName(name);
            category.setCreatedAt(LocalDateTime.now());
            category.setUpdatedAt(LocalDateTime.now());
            category.setImageUrl(null);  // No image URL

            // Save the category in the database (assuming a categoryRepository is available)
            // categoryRepository.save(category);

            // Return the created category in the response
            return ResponseEntity.ok(category);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Integer id,
            @RequestPart("category") Category category,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, category, file);
            return ResponseEntity.ok(updatedCategory);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
