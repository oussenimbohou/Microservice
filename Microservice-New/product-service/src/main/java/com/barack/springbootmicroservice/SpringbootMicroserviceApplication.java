package com.barack.springbootmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringbootMicroserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringbootMicroserviceApplication.class, args);
	}
}
