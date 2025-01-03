package com.example.easyMart.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.easyMart.Entity.Product;
import com.example.easyMart.Entity.ProductCategory;
import com.example.easyMart.Repository.ProductRepository;
import com.example.easyMart.Service.ProductService;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public Product addProductWithImages(
            @RequestParam("productDetails") String productDetails, // Get product details as JSON string
            @RequestParam("productImages") List<MultipartFile> images // Get images as list of MultipartFile
    ) {
        // Convert JSON string to Product object
        Product product = productService.convertJsonToProduct(productDetails);
        
        // Handle image upload and assign images to the product
        return productService.addProductWithImages(product, images);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return ResponseEntity.ok(productService.updateProduct(id, productDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/category/{categoryName}")
    public List<Product> getProductsByCategory(@PathVariable String categoryName) {
        return  productService.getProductsByCategory(categoryName);
        
    }
    
    
    @GetMapping("/filters/{categoryName}")
    public Map<String, List<String>> getFilters(@PathVariable String categoryName) {
        Map<String, List<String>> filters = new HashMap<>();
        System.out.println("selected value:"+categoryName);
        filters.put("brands", productService.findDistinctBrands(categoryName));
        filters.put("shippingLocation", productService.findDistinctShippingLocations(categoryName));
        return filters;
    }
    
    @GetMapping("/filtered")
    public ResponseEntity<List<Product>> getFilteredProducts(
            @RequestParam(required = false) String productCategory,
            @RequestParam(required = false) List<String> productBrand,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minDiscount,
            @RequestParam(required = false) Double maxDiscount,
            @RequestParam(required = false) List<String> productShippingLocation
    ) {
    	System.out.println("inside");
        List<Product> filteredProducts = productService.filterProducts(productCategory, productBrand, minPrice, maxPrice, minDiscount, maxDiscount, productShippingLocation);
        return ResponseEntity.ok(filteredProducts);
    }
}

