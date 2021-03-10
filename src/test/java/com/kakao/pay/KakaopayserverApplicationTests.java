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
	@Order(3)
	public void getProducts() throws JsonProcessingException, Exception {
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
//	@Test
	@Order(2)
	public void getInvest() throws JsonProcessingException, Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("X-USER-ID", String.valueOf(1));
		
		/**
		InvestRequestVO request = new InvestRequestVO();
		request.setAmount(500);
		request.setProductId((long) 1);
		
		mockMvc.perform(post("/invest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.characterEncoding("UTF-8")
				.headers(httpHeaders)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("E000"));
		*/
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
	@Order(1)
	public void makeInvest() throws JsonProcessingException, Exception {
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
		
		request = new InvestRequestVO();
		request.setAmount(2210000);
		request.setProductId((long) 1);
		
		mockMvc.perform(post("/invest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.characterEncoding("UTF-8")
				.headers(httpHeaders)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("E002"));
		
		request = new InvestRequestVO();
		request.setAmount(500);
		request.setProductId((long) 1);
		
		mockMvc.perform(post("/invest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				.characterEncoding("UTF-8")
				.headers(httpHeaders)
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("E000"));
		
		request = new InvestRequestVO();
		request.setAmount(500);
		request.setProductId((long) 1);
		
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
