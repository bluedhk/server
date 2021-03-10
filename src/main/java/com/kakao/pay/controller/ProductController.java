package com.kakao.pay.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakao.pay.service.ProductService;
import com.kakao.pay.vo.ProductResponseVO;

@RestController
public class ProductController {
	private ProductService productService;
	
	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

	@GetMapping("/products")
	public List<ProductResponseVO> getProducts(HttpServletRequest request){
		return productService.getProducts();
	}
}
