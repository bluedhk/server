package com.kakao.pay.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kakao.pay.service.InvestService;
import com.kakao.pay.vo.InvestRequestVO;
import com.kakao.pay.vo.InvestResponseVO;

@RestController
public class InvestController {
	private static final Logger logger = LoggerFactory.getLogger(InvestController.class);
	private InvestService investService;
	

	public InvestController(InvestService investService) {
		super();
		this.investService = investService;
	}
	
	@PostMapping("/invest")
	public Map<String, Object> makeInvest(HttpServletRequest request, @RequestBody InvestRequestVO investRequestVO){
		int userId = Integer.parseInt(String.valueOf(request.getAttribute("userId") == null ? "0" : request.getAttribute("userId")));
		return investService.makeInvest(userId, investRequestVO);
	}
	
	@GetMapping("/invest")
	public List<InvestResponseVO> getInvest(HttpServletRequest request){
		int userId = Integer.parseInt(String.valueOf(request.getAttribute("userId") == null ? "0" : request.getAttribute("userId")));
		return investService.getInvests(userId);
	}
}
