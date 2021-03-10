package com.kakao.pay.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kakao.pay.model.Invest;
import com.kakao.pay.model.Product;

public interface InvestRepository extends JpaRepository<Invest, Long>{
	
	public List<Invest> findByProduct(Product product);
	
	@Query(value = "SELECT CAST(COALESCE(SUM(i.amount), 0) AS int) as AMOUNT, CAST(COALESCE(COUNT(1), 0) as int) as CNT FROM Invest i WHERE i.product_id = :productId", nativeQuery = true)
	public Map<String, Object> findSubAmountByProductId(Long productId);
	
	public List<Invest> findByUserId(int userId);
}
