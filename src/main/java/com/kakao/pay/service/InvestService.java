package com.kakao.pay.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakao.pay.enums.ReturnMessages;
import com.kakao.pay.model.Invest;
import com.kakao.pay.model.Product;
import com.kakao.pay.repository.InvestRepository;
import com.kakao.pay.repository.ProductRepository;
import com.kakao.pay.vo.InvestRequestVO;
import com.kakao.pay.vo.InvestResponseVO;
import com.kakao.pay.vo.ProductResponseVO;

@Service
public class InvestService {
	private static final Logger logger = LoggerFactory.getLogger(InvestService.class);
	private ProductRepository productRepository;
	private InvestRepository investRepository;

	public InvestService(ProductRepository productRepository, InvestRepository investRepository) {
		super();
		this.productRepository = productRepository;
		this.investRepository = investRepository;
	}


	@Transactional(readOnly = true)
	public List<InvestResponseVO> getInvests(int userId){
		List<InvestResponseVO> investResponseVOs = new ArrayList<>();
		
		List<Invest> invests = investRepository.findByUserId(userId);
		for(Invest i : invests) {
			Product p = i.getProduct();
			
			InvestResponseVO investResponseVO = InvestResponseVO.builder()
					.amount(i.getAmount())
					.investedAt(i.getInvestedAt())
					.productId(p.getProductId())
					.title(p.getTitle())
					.totalInvestingAmount(p.getTotalInvestingAmount())
					.build();
			
			investResponseVOs.add(investResponseVO);
		}
		
		return investResponseVOs;
	}
	
	@Transactional
	public Map<String, Object> makeInvest(int userId, InvestRequestVO investRequestVO) {
		Long productId = investRequestVO.getProductId();

		Product product = productRepository.findByProductId(productId);
		if (product == null) {
			return makeReturnMessage(ReturnMessages.E001);
		}
		int totalAmount = product.getTotalInvestingAmount();

		Map<String, Object> investMap = investRepository.findSubAmountByProductId(productId);
		int subAmount = investMap.get("amount") == null ? 0 : (int) investMap.get("amount");
		
		if (totalAmount <= subAmount) {
			return makeReturnMessage(ReturnMessages.E003);
		}

		subAmount += investRequestVO.getAmount();
		if (totalAmount < subAmount) {
			return makeReturnMessage(ReturnMessages.E002);
		}

		Invest invest = new Invest();
		invest.setAmount(investRequestVO.getAmount());
		invest.setInvestedAt(Date.from(ZonedDateTime.now().toInstant()));
		invest.setProduct(product);
		invest.setUserId(userId);
		invest = investRepository.save(invest);
		logger.debug("insert invest : {}", invest.toString());

		return makeReturnMessage(ReturnMessages.E000);

	}

	private Map<String, Object> makeReturnMessage(ReturnMessages message) {
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("message", message.getMessage());
		returnMap.put("code", message.getCode());
		return returnMap;
	}
}
