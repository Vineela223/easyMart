package com.example.easyMart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.easyMart.Entity.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long>{

}
