package com.example.easyMart.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.easyMart.Entity.ProductCategory;
import com.example.easyMart.Repository.ProductCategoryRepository;

import java.util.List;

@Service
public class CategoryService {

	@Autowired
    private  ProductCategoryRepository categoryRepository;


    public List<ProductCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public ProductCategory addCategory(ProductCategory category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
