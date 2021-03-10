package com.kakao.pay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakao.pay.model.Invest;

public interface InvestRepository extends JpaRepository<Invest, Long>{
	
	public List<Invest> findByProductId(Long productId);
	
	public List<Invest> findByUserId(int userId);
}
