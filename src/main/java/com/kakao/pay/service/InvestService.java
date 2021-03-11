package com.kakao.pay.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakao.pay.enums.ReturnMessages;
import com.kakao.pay.model.Invest;
import com.kakao.pay.model.Product;
import com.kakao.pay.repository.InvestRepository;
import com.kakao.pay.repository.ProductRepository;
import com.kakao.pay.vo.InvestRequestVO;
import com.kakao.pay.vo.InvestResponseVO;

@Service
public class InvestService {
	@PersistenceContext
	private EntityManager entityManager;

	private ProductRepository productRepository;
	private InvestRepository investRepository;

	public InvestService(ProductRepository productRepository, InvestRepository investRepository) {
		super();
		this.productRepository = productRepository;
		this.investRepository = investRepository;
	}

	@Transactional(readOnly = true)
	public List<InvestResponseVO> getInvests(int userId) {
		List<InvestResponseVO> investResponseVOs = new ArrayList<>();

		List<Invest> invests = investRepository.findByUserId(userId);
		for (Invest i : invests) {
			Product p = i.getProduct();

			InvestResponseVO investResponseVO = InvestResponseVO.builder().amount(i.getAmount())
					.investedAt((Date) i.getInvestedAt().clone()).productId(p.getProductId()).title(p.getTitle())
					.totalInvestingAmount(p.getTotalInvestingAmount()).build();

			investResponseVOs.add(investResponseVO);
		}

		return investResponseVOs;
	}

	@Transactional
	public Map<String, Object> makeInvest(InvestRequestVO investRequestVO) {
		Long productId = investRequestVO.getProductId();
		int userId = investRequestVO.getUserId();

		Product product = productRepository.findByProductId(productId);
		if (product == null) {
			return makeReturnMessage(ReturnMessages.E001);
		}
		int totalAmount = product.getTotalInvestingAmount();
		Map<String, Object> investMap = investRepository.findSubAmountByProductId(product);
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

		return makeReturnMessage(ReturnMessages.E000);

	}

	private Map<String, Object> makeReturnMessage(ReturnMessages message) {
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("message", message.getMessage());
		returnMap.put("code", message.getCode());
		return returnMap;
	}
}
