package com.kakao.pay.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseVO implements Serializable{
	private static final long serialVersionUID = -2085552688480481424L;
	private Long productId;
	private String title;
	private int totalInvestingAmount;
	private Date startedAt;
	private Date finishedAt;
	private int investCount;
	private int investSubTotal;
	private String investState;
}
