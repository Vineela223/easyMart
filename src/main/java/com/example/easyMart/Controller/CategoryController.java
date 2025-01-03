package com.example.easyMart.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.easyMart.Entity.ProductCategory;
import com.example.easyMart.Service.CategoryService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
 
	@Autowired
    private  CategoryService categoryService;

    @GetMapping
    public List<ProductCategory> getCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/add")
    public ProductCategory addCategory(@RequestBody ProductCategory category) {
        return categoryService.addCategory(category);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
