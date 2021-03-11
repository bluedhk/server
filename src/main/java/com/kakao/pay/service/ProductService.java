package com.kakao.pay.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakao.pay.model.Product;
import com.kakao.pay.repository.InvestRepository;
import com.kakao.pay.vo.ProductResponseVO;

@Service
public class ProductService {
	private InvestRepository investRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public ProductService(InvestRepository investRepository) {
		super();
		this.investRepository = investRepository;
	}

	@Transactional(readOnly = true)
	public List<ProductResponseVO> getProducts() {

		ZonedDateTime now = ZonedDateTime.now();

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);

		Predicate onStart = criteriaBuilder.lessThanOrEqualTo(root.get("startedAt"), Date.from(now.toInstant()));
		Predicate onEnd = criteriaBuilder.greaterThanOrEqualTo(root.get("finishedAt"), Date.from(now.toInstant()));
		List<Predicate> conditions = new ArrayList<Predicate>();
		conditions.add(onStart);
		conditions.add(onEnd);

		criteriaQuery.select(root).where(conditions.toArray(new Predicate[] {}));

		List<Product> products = entityManager.createQuery(criteriaQuery).getResultList();

		List<ProductResponseVO> responseProducts = new ArrayList<ProductResponseVO>();

		for (Product p : products) {
			int total = p.getTotalInvestingAmount();
			Map<String, Object> investMap = investRepository.findSubAmountByProductId(p);
			int subTotal = investMap.get("amount") == null ? 0 : (int) investMap.get("amount");
			int cnt = investMap.get("cnt") == null ? 0 : (int) investMap.get("cnt");

			boolean state = false;
			if (total > subTotal) {
				state = true;
			}

			ProductResponseVO productResponseVO = ProductResponseVO.builder().investCount(cnt)
					.investState(state ? "모집중" : "모집완료").investSubTotal(subTotal).productId(p.getProductId())
					.startedAt((Date) p.getStartedAt().clone()).title(p.getTitle())
					.totalInvestingAmount(p.getTotalInvestingAmount()).finishedAt((Date) p.getFinishedAt().clone())
					.build();
			responseProducts.add(productResponseVO);
		}
		return responseProducts;
	}
}
