package com.kakao.pay.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "product")
public class Product {
	@Id
	@Column(length = 20, nullable = false, name = "product_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
		
	private int investCount;
	private int investSubTotal;
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
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getTotalInvestingAmount() {
		return totalInvestingAmount;
	}
	public void setTotalInvestingAmount(int totalInvestingAmount) {
		this.totalInvestingAmount = totalInvestingAmount;
	}
	public Date getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}
	public Date getFinishedAt() {
		return finishedAt;
	}
	public void setFinishedAt(Date finishedAt) {
		this.finishedAt = finishedAt;
	}
	public int getInvestCount() {
		return investCount;
	}
	public void setInvestCount(int investCount) {
		this.investCount = investCount;
	}
	public int getInvestSubTotal() {
		return investSubTotal;
	}
	public void setInvestSubTotal(int investSubTotal) {
		this.investSubTotal = investSubTotal;
	}
	public String getInvestState() {
		return investState;
	}
	public void setInvestState(String investState) {
		this.investState = investState;
	}
	
	
}
