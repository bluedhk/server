package com.kakao.pay.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.kakao.pay.model.Product;
import com.kakao.pay.service.InvestService;
import com.kakao.pay.vo.InvestRequestVO;

@RestController
public class InvestController {

	private InvestService investService;
	
	public InvestController(InvestService investService) {
		super();
		this.investService = investService;
	}
	
	@PostMapping("/products")
	public List<Product> saveProduct() {
		return investService.saveProduct();
	}

	@GetMapping("/products")
	public List<Product> getProducts(@RequestHeader("X-USER-ID") int userId){
		return investService.getProducts();
	}
	
	@PostMapping("/invest")
	public Map<String, Object> makeInvest(@RequestHeader("X-USER-ID") int userId, @RequestBody InvestRequestVO investRequestVO){
		return investService.makeInvest(userId, investRequestVO);
	}
	
	@GetMapping("/invest")
	public List<Map<String, Object>> getInvest(@RequestHeader("X-USER-ID") int userId){
		return investService.getInvests(userId);
	}
}
