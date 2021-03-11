package com.kakao.pay.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "product")
@Table(indexes = { @Index(name = "product_idx1", columnList = "started_at"),
		@Index(name = "product_idx2", columnList = "finished_at") })
@Getter
@Setter
public class Product {
	@Id
	@Column(length = 20, nullable = false, name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@Column(name = "title")
	@NotNull
	private String title;
	@Column(name = "total_investing_amount")
	@NotNull
	private int totalInvestingAmount;
	@Column(name = "started_at")
	@NotNull
	private Date startedAt;
	@Column(name = "finished_at")
	@NotNull
	private Date finishedAt;

	@Transient
	private int investCount;
	@Transient
	private int investSubTotal;
	@Transient
	private String investState;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("product [productId=");
		builder.append(productId);
		builder.append(", title=");
		builder.append(title);
		builder.append(", totalInvestingAmount=");
		builder.append(totalInvestingAmount);
		builder.append(", startedAt=");
		builder.append(startedAt);
		builder.append(", finishedAt=");
		builder.append(finishedAt);
		builder.append("]");
		return builder.toString();
	}
}
