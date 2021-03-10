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

	public List<Product> saveProduct() {
		Product p1 = new Product();
		Product p2 = new Product();

		ZonedDateTime now = ZonedDateTime.now();

		p1.setFinishedAt(Date.from(now.plusMinutes(5).toInstant()));
		p1.setStartedAt(Date.from(now.toInstant()));
		p1.setTitle("개인신용 포트폴리오");
		p1.setTotalInvestingAmount(1000);

		p2.setFinishedAt(Date.from(now.plusMinutes(5).toInstant()));
		p2.setStartedAt(Date.from(now.toInstant()));
		p2.setTitle("부동산 포트폴리오");
		p2.setTotalInvestingAmount(1000000);

		productRepository.save(p1);
		productRepository.save(p2);

		List<Product> products = getProducts();
		return products;
	}

	public List<Product> getProducts() {

		ZonedDateTime now = ZonedDateTime.now();
		List<Product> products = productRepository.findAll();
		products.removeIf(p -> p.getStartedAt().after(Date.from(now.toInstant()))
				|| p.getFinishedAt().before(Date.from(now.toInstant())));

		for (Product p : products) {
			List<Invest> invests = investRepository.findByProductId(p.getProductId());
			int total = p.getTotalInvestingAmount();
			int subTotal = 0;
			for (Invest i : invests) {
				subTotal += i.getAmount();
			}
			p.setInvestCount(invests.size());
			p.setInvestSubTotal(subTotal);

			boolean state = false;
			if (total > subTotal) {
				state = true;
			}
			p.setInvestState(state ? "모집중" : "모집완료");

		}
		return products;
	}

	public List<Map<String, Object>> getInvests(int userId){
		List<Map<String, Object>> returnMap = new ArrayList<Map<String,Object>>();
		List<Invest> invests = investRepository.findByUserId(userId);
		for(Invest i : invests) {
			
			Product p = productRepository.findById(i.getProductId()).get();
			
			Map<String, Object> inputMap = new HashMap<String, Object>();
			inputMap.put("productId", p.getProductId());
			inputMap.put("title", p.getTitle());
			inputMap.put("totalInvestingAmount", p.getTotalInvestingAmount());
			inputMap.put("amount", i.getAmount());
			inputMap.put("investedAt", i.getInvestedAt());
			
			returnMap.add(inputMap);
		}
		
		return returnMap;
	}
	
	@Transactional
	public Map<String, Object> makeInvest(int userId, InvestRequestVO investRequestVO) {
		Long productId = investRequestVO.getProductId();

		Product product = productRepository.findByProductId(productId);
		if (product == null) {
			return makeReturnMessage(ReturnMessages.E001);
		}
		int totalAmount = product.getTotalInvestingAmount();

		List<Invest> invests = investRepository.findByProductId(productId);
		int subAmount = 0;
		for (Invest i : invests) {
			subAmount += i.getAmount();
		}

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
		invest.setProductId(productId);
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
