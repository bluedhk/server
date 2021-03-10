package com.kakao.pay.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity(name = "invest")
@Table(indexes = {@Index(name = "invest_idx1", columnList = "product_id"), @Index(name = "invest_idx2", columnList = "user_id")})
public class Invest {
	@Id
	@Column(length = 20, nullable = false, name = "invest_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long investId;
	
//	@Column(name = "product_id")
//	@NotNull
//	private Long productId;
//	
	@ManyToOne(targetEntity=Product.class, fetch=FetchType.LAZY) 
	@JoinColumn(name="product_id") 
	private Product product;
	
	@Column(name = "user_id")
	@NotNull
	private int userId;
	@Column(name = "invested_at")
	private Date investedAt;
	@Column(name = "amount")
	@NotNull
	private int amount;
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Long getInvestId() {
		return investId;
	}
	public void setInvestId(Long investId) {
		this.investId = investId;
	}
//	public Long getProductId() {
//		return productId;
//	}
//	public void setProductId(Long productId) {
//		this.productId = productId;
//	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getInvestedAt() {
		return investedAt;
	}
	public void setInvestedAt(Date investedAt) {
		this.investedAt = investedAt;
	}
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Invest [investId=");
		builder.append(investId);
		builder.append(", productId=");
		builder.append(product.getProductId());
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", investedAt=");
		builder.append(investedAt);
		builder.append(", amount=");
		builder.append(amount);
		builder.append("]");
		return builder.toString();
	}

	
	
}
