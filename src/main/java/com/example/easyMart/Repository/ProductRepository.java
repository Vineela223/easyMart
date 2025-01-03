package com.example.easyMart.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.easyMart.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("SELECT p FROM Product p LEFT JOIN FETCH p.productImages")
    List<Product> findAllWithImages();
	
	 @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE p.id = :productId")
	    Optional<Product> findByIdWithImages(@Param("productId") Long productId);

	 @Query("SELECT p FROM Product p WHERE p.productCategory = :categoryName")
	    List<Product> findByCategoryName(@Param("categoryName") String categoryName);
	

	    @Query("SELECT DISTINCT p.productBrand FROM Product p WHERE p.productCategory = :categoryName")
	    List<String> findDistinctBrands(@Param("categoryName") String categoryName);

	    @Query("SELECT DISTINCT p.productShippingLocation FROM Product p WHERE p.productCategory = :categoryName")
		List<String> findDistinctShippingLocations(@Param("categoryName") String categoryName);

	    @Query("SELECT p FROM Product p " +
	    	       "WHERE (:categoryName IS NULL OR p.productCategory = :categoryName) " +
	    	       "AND (:brands IS NULL OR p.productBrand IN :brands) " +
	    	       "AND (:minPrice IS NULL OR :maxPrice IS NULL OR p.productPrice BETWEEN :minPrice AND :maxPrice) " +
	    	       "AND (:minDiscount IS NULL OR :maxDiscount IS NULL OR p.productDiscount BETWEEN :minDiscount AND :maxDiscount) " +
	    	       "AND (:locations IS NULL OR p.productShippingLocation IN :locations)")
	    List<Product> findFilteredProducts(
	            @Param("categoryName") String categoryName,
	            @Param("brands") List<String> brands,
	            @Param("minPrice") Double minPrice,
	            @Param("maxPrice") Double maxPrice,
	            @Param("minDiscount") Double minDiscount,
	            @Param("maxDiscount") Double maxDiscount,
	            @Param("locations") List<String> locations);
	
}
