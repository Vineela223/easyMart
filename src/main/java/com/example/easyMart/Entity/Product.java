package com.example.easyMart.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_Id")
    private Long productId;

    private String productName;
    @Column(length = 1000)
    private String productDescription;
    private String productCode;
    
   
    private String productCategory;
    
    private String productBrand;
    private String productColor;
    private String productSize;
    private String productWeight;
    private String productMaterial;
    private int productQuantity;
    private String productUnit;
    private BigDecimal productPrice;
    private BigDecimal salesPrice;
    private BigDecimal productDiscount;
    private BigDecimal productTax;
    private BigDecimal productShippingCharge;
    @Column(name="product_Shipping_Location")
    private String productShippingLocation;
    @Column(length = 2000)
    private String productShippingReturnPolicy;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductImage> productImages;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private List<ProductReviews> productReviews;
    
    
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getProductBrand() {
		return productBrand;
	}
	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}
	public String getProductColor() {
		return productColor;
	}
	public void setProductColor(String productColor) {
		this.productColor = productColor;
	}
	public String getProductSize() {
		return productSize;
	}
	public void setProductSize(String productSize) {
		this.productSize = productSize;
	}
	public String getProductWeight() {
		return productWeight;
	}
	public void setProductWeight(String productWeight) {
		this.productWeight = productWeight;
	}
	public String getProductMaterial() {
		return productMaterial;
	}
	public void setProductMaterial(String productMaterial) {
		this.productMaterial = productMaterial;
	}
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	public String getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	public BigDecimal getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}
	public BigDecimal getProductDiscount() {
		return productDiscount;
	}
	public void setProductDiscount(BigDecimal productDiscount) {
		this.productDiscount = productDiscount;
	}
	public BigDecimal getProductTax() {
		return productTax;
	}
	public void setProductTax(BigDecimal productTax) {
		this.productTax = productTax;
	}
	public BigDecimal getProductShippingCharge() {
		return productShippingCharge;
	}
	public void setProductShippingCharge(BigDecimal productShippingCharge) {
		this.productShippingCharge = productShippingCharge;
	}
	public String getProductShippingLocation() {
		return productShippingLocation;
	}
	public void setProductShippingLocation(String productShippingLocation) {
		this.productShippingLocation = productShippingLocation;
	}
	public String getProductShippingReturnPolicy() {
		return productShippingReturnPolicy;
	}
	public void setProductShippingReturnPolicy(String productShippingReturnPolicy) {
		this.productShippingReturnPolicy = productShippingReturnPolicy;
	}
	public List<ProductImage> getProductImages() {
		return productImages;
	}
	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}
	public List<ProductReviews> getProductReviews() {
		return productReviews;
	}
	public void setProductReviews(List<ProductReviews> productReviews) {
		this.productReviews = productReviews;
	}
   
}

