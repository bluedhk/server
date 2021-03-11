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

import lombok.Getter;
import lombok.Setter;

@Entity(name = "invest")
@Table(indexes = { @Index(name = "invest_idx1", columnList = "product_id"),
		@Index(name = "invest_idx2", columnList = "user_id") })
@Getter
@Setter
public class Invest {
	@Id
	@Column(length = 20, nullable = false, name = "invest_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long investId;

	@ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "user_id")
	@NotNull
	private int userId;
	@Column(name = "invested_at")
	private Date investedAt;
	@Column(name = "amount")
	@NotNull
	private int amount;

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
