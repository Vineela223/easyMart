package com.example.easyMart.Entity;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Product_Category")
@Data
public class ProductCategory {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment categoryId
	 @Column(name = "category_id") // Make sure the column names match
	    private Long categoryId;

	    @Column(name = "category_name", nullable = false)
	    private String categoryName;

	    @Column(name = "description")
	    private String description;

	    @Column(name = "created_at", updatable = false)
	    private LocalDateTime createdAt;

	    @Column(name = "updated_at")
	    private LocalDateTime updatedAt;
	    
	    
	    public ProductCategory() {}

	    public ProductCategory(String categoryName, String description) {
	        this.categoryName = categoryName;
	        this.description = description;
	        this.createdAt = LocalDateTime.now();
	        this.updatedAt = LocalDateTime.now();
	    }

		public Long getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(Long categoryId) {
			this.categoryId = categoryId;
		}

		public String getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}
	    
	    
		@PrePersist
	    @PreUpdate
	    public void updateTimestamps() {
	        if (this.createdAt == null) {
	            this.createdAt = LocalDateTime.now();
	        }
	        this.updatedAt = LocalDateTime.now();
	    }

}
