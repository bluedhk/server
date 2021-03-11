package com.kakao.pay.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kakao.pay.model.Invest;
import com.kakao.pay.model.Product;

public interface InvestRepository extends JpaRepository<Invest, Long> {

	public List<Invest> findByProduct(Product product);

	@Query("SELECT CAST(COALESCE(SUM(i.amount), 0) AS int) as amount, CAST(COALESCE(COUNT(1), 0) as int) as cnt FROM invest i WHERE i.product = :product")
	public Map<String, Object> findSubAmountByProductId(@Param("product") Product product);

	public List<Invest> findByUserId(int userId);
}
