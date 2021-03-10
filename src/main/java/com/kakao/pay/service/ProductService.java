package com.kakao.pay.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakao.pay.model.Product;
import com.kakao.pay.repository.InvestRepository;
import com.kakao.pay.repository.ProductRepository;
import com.kakao.pay.vo.ProductResponseVO;

@Service
public class ProductService {
	private ProductRepository productRepository;
	private InvestRepository investRepository;

	
	public ProductService(ProductRepository productRepository, InvestRepository investRepository) {
		super();
		this.productRepository = productRepository;
		this.investRepository = investRepository;
	}


	@Transactional(readOnly = true)
	public List<ProductResponseVO> getProducts() {

		ZonedDateTime now = ZonedDateTime.now();
		List<Product> products = productRepository.findAll();
		products.removeIf(p -> p.getStartedAt().after(Date.from(now.toInstant()))
				|| p.getFinishedAt().before(Date.from(now.toInstant())));

		List<ProductResponseVO> responseProducts = new ArrayList<ProductResponseVO>();
		
		for (Product p : products) {
			int total = p.getTotalInvestingAmount();
			Map<String, Object> investMap = investRepository.findSubAmountByProductId(p.getProductId());
			int subTotal = investMap.get("amount") == null ? 0 : (int) investMap.get("amount");
			int cnt = investMap.get("cnt") == null ? 0 : (int) investMap.get("cnt");

			boolean state = false;
			if (total > subTotal) {
				state = true;
			}
			
			ProductResponseVO productResponseVO = ProductResponseVO.builder()
					.investCount(cnt)
					.investState(state ? "모집중" : "모집완료")
					.investSubTotal(subTotal)
					.productId(p.getProductId())
					.startedAt(p.getStartedAt())
					.title(p.getTitle())
					.totalInvestingAmount(p.getTotalInvestingAmount())
					.finishedAt(p.getFinishedAt())
					.build();
			responseProducts.add(productResponseVO);
		}
		return responseProducts;
	}
}
