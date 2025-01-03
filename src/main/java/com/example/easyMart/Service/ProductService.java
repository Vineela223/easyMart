package com.example.easyMart.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.easyMart.Entity.Product;
import com.example.easyMart.Entity.ProductCategory;
import com.example.easyMart.Entity.ProductImage;
import com.example.easyMart.Repository.ProductCategoryRepository;
import com.example.easyMart.Repository.ProductImageRepository;
import com.example.easyMart.Repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
	private ProductImageRepository productImageRepository;
    
    @Autowired
    private ProductCategoryRepository productCategory;

    
    public List<ProductCategory> getAllCategories() {
        return productCategory.findAll();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllWithImages();
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findByIdWithImages(productId);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getProductsByCategory(String categoryName) {
        // Create a custom query to fetch products based on the category
        return productRepository.findByCategoryName(categoryName);
    }
    public Product updateProduct(Long productId, Product productDetails) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setProductName(productDetails.getProductName());
        product.setProductDescription(productDetails.getProductDescription());
        product.setProductCode(productDetails.getProductCode());
        product.setProductCategory(productDetails.getProductCategory());
        product.setProductBrand(productDetails.getProductBrand());
        product.setProductColor(productDetails.getProductColor());
        product.setProductSize(productDetails.getProductSize());
        product.setProductWeight(productDetails.getProductWeight());
        product.setProductMaterial(productDetails.getProductMaterial());
        product.setProductQuantity(productDetails.getProductQuantity());
        product.setProductUnit(productDetails.getProductUnit());
        product.setProductPrice(productDetails.getProductPrice());
        product.setSalesPrice(productDetails.getSalesPrice());
        product.setProductDiscount(productDetails.getProductDiscount());
        product.setProductTax(productDetails.getProductTax());
        product.setProductShippingCharge(productDetails.getProductShippingCharge());
        product.setProductShippingLocation(productDetails.getProductShippingLocation());
        product.setProductShippingReturnPolicy(productDetails.getProductShippingReturnPolicy());
        product.setProductImages(productDetails.getProductImages());

        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }


	private String saveImageFile(MultipartFile imageFile) {
        try {
            // Define where to save the images, e.g., "uploads/"
            String uploadDir = System.getProperty("user.dir") + "/uploads";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Generate a unique name for the image
            String imageName = System.currentTimeMillis() + "-" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, imageName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);



            // Return the image URL (assuming you can access it via a URL)
            return "/uploads/" + imageName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image file", e);
        }
    }

	public Product addProductWithImages(Product productDetails, List<MultipartFile> images) {
		
		
		Product product = productRepository.save(productDetails);

		// Handle image uploads and associate them with the product
		ProductImage productImage = new ProductImage();  // Create a single ProductImage instance

		for (int i = 0; i < images.size(); i++) {
		    MultipartFile imageFile = images.get(i);
		    String imageUrl = saveImageFile(imageFile);  // Implement saveImageFile to save the image and return the URL

		    // Assign each image URL to a different column
		    if (i == 0) {
		        productImage.setImage1(imageUrl);;
		    } else if (i == 1) {
		        productImage.setImage2(imageUrl);
		    } else if (i == 2) {
		        productImage.setImage3(imageUrl);
		    }
		}

		// Set the product reference in the ProductImage entity
		productImage.setProduct(product);

		// Save the single ProductImage record associated with the product
		productImageRepository.save(productImage);

		// Associate the saved ProductImage with the product
		List<ProductImage> productImages = Collections.singletonList(productImage);  // Wrap in a list
		product.setProductImages(productImages);

		// Save the product with the associated images (if needed, based on your repository setup)
		return product;  // Return the product with images

    }

	public Product convertJsonToProduct(String productDetails) {
        // Deserialize the productDetails JSON string into Product object
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(productDetails, Product.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Or handle it in another way
        }
    }

	public List<String> findDistinctBrands(String categoryName) {
		return productRepository.findDistinctBrands(categoryName);
	}

	public List<String> findDistinctShippingLocations(String categoryName) {
		return productRepository.findDistinctShippingLocations(categoryName);
	}

	public List<Product> filterProducts(String category, List<String> brands, Double minPrice, Double maxPrice,
			Double minDiscount, Double maxDiscount, List<String> shippingLocations) {
		// TODO Auto-generated method stub
		return productRepository.findFilteredProducts(category,brands,minPrice,maxPrice,minDiscount,maxDiscount,shippingLocations);
	}
}
