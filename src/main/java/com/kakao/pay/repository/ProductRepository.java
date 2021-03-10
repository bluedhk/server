package com.kakao.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakao.pay.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	Product findByProductId(Long productId);
}
