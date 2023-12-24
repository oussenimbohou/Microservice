package com.barack.springbootmicroservice;

import com.barack.springbootmicroservice.dto.ProductRequest;
import com.barack.springbootmicroservice.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class SpringbootMicroserviceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository repository;

	@Container
	static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql");

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry properties) {
		properties.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		properties.add("spring.datasource.username", mySQLContainer::getUsername);
		properties.add("spring.datasource.password", mySQLContainer::getPassword);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString))
				.andExpect(status().isCreated());
        Assertions.assertEquals(1, repository.findAll().size());
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("iPhone")
				.price(BigDecimal.valueOf(300.0))
				.description("Apple Phone")
				.build();
	}
}

