package com.kakao.pay.common.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class CommonIntercepter implements HandlerInterceptor{
	private static final Logger logger = LoggerFactory.getLogger(CommonIntercepter.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String xUserId = request.getHeader("X-USER-ID");
		request.setAttribute("userId", xUserId);
		return true;
	}
}
