package com.kakao.pay;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.pay.vo.InvestRequestVO;

@SpringBootTest
@AutoConfigureMockMvc
class KakaopayserverApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(KakaopayserverApplicationTests.class);
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Test
	void contextLoads() {
	}
	
	@SuppressWarnings("deprecation")
	@Test
	@Order(1)
	public void getProductsTest() throws JsonProcessingException, Exception {
		/**
		 * 전체 투자 상품 조회
		 */
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("X-USER-ID", String.valueOf(1));
		
		mockMvc.perform(get("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.headers(httpHeaders)
			).andDo(print())
			.andExpect(status().isOk());
	}
	
	
	@SuppressWarnings("deprecation")
	@Test
	@Order(2)
	public void makeInvestTest() throws JsonProcessingException, Exception {
		/**
		 * 투자하기
		 */
		InvestRequestVO request = new InvestRequestVO();
		request.setAmount(500);
		request.setProductId((long) 1);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("X-USER-ID", String.valueOf(1));
		
		mockMvc.perform(post("/invest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.characterEncoding("UTF-8")
				.headers(httpHeaders)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("E000"));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	@Order(3)
	public void getInvestTest() throws JsonProcessingException, Exception {
		/**
		 * 내 투자상품 조회하기
		 */
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("X-USER-ID", String.valueOf(1));
		
		mockMvc.perform(get("/invest")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.headers(httpHeaders)
			).andDo(print())
			.andExpect(status().isOk());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	@Order(4)
	public void errorCaseTest() throws JsonProcessingException, Exception {
		/**
		 * 전체 테스트 케이스 실행
		 * 총모집금액 1000
		 * 1. 500 투자 : 성공
		 * 2. 500 투자 : 성공
		 * 3. 500 투자 : SOLD OUT
		 * 4. 투자 내역 조회
		 */
		InvestRequestVO request = new InvestRequestVO();
		request.setAmount(500);
		request.setProductId((long) 3);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("X-USER-ID", String.valueOf(1));
		
		mockMvc.perform(post("/invest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.characterEncoding("UTF-8")
				.headers(httpHeaders)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("E000"));
		
		request.setAmount(2210000);
		
		mockMvc.perform(post("/invest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.characterEncoding("UTF-8")
				.headers(httpHeaders)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("E002"));
		
		request.setAmount(500);
		
		mockMvc.perform(post("/invest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.characterEncoding("UTF-8")
				.headers(httpHeaders)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("E000"));
		
		request.setAmount(500);
		
		mockMvc.perform(post("/invest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.characterEncoding("UTF-8")
				.headers(httpHeaders)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("E003"));
		
		mockMvc.perform(get("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.headers(httpHeaders)
			).andDo(print())
			.andExpect(status().isOk());
	}
}
