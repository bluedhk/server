package com.kakao.pay.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Transactional;

import com.kakao.pay.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Product findByProductId(Long productId);
}
