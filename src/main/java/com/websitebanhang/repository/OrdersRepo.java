package com.websitebanhang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.websitebanhang.entitys.Orders;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Long>{
	
}
