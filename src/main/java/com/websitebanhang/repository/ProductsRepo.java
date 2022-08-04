package com.websitebanhang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.websitebanhang.entitys.Products;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Long> {
	List<Products> findByProductTypes_Id(Long typeId);
	Products findBySlug(String slug);
	
	// viết theo truy vấn sql 
	@Query(value = "select * from products where isDeleted = 0 and quantity > 0",
			nativeQuery = true)
	List<Products> findAllAvailable();
	
	// viết truy vấn theo String_data_jpa phiên dịch câu sql
	List<Products> findByIsDeletedAndQuantityGreaterThan(Boolean isDeketed,Integer quantity);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE products SET quantity = ?1 WHERE id= ?2",
			nativeQuery = true)
	void updateQuantity(Integer newQuantity, Long id);
}
