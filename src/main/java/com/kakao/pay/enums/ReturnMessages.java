package com.kakao.pay.enums;

public enum ReturnMessages {
	E000("SUCCESS.", "E000"),
	E001("product is not available.", "E001"),
	E002("Total investment amount is insufficient.", "E002"),
	E003("SOLD OUT.", "E003"),
	;
	
	private ReturnMessages(String message, String code) {
		this.message = message;
		this.code = code;
	}
	String message;
	String code;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
