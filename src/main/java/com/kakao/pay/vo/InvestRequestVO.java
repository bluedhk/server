package com.kakao.pay.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestRequestVO {
	private Long productId;
	private int amount;
	private int userId;
}
