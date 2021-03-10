package com.kakao.pay.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvestResponseVO implements Serializable {
	private static final long serialVersionUID = -7050974998860440768L;
	private Long productId;
	private String title;
	private int totalInvestingAmount;
	private int amount;
	private Date investedAt;
}
