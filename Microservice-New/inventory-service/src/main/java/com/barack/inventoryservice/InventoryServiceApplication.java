package com.barack.inventoryservice;

import com.barack.inventoryservice.entity.Inventory;
import com.barack.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository repository){
		return args -> {
			Inventory inventory1 = Inventory.builder()
					.skuCode("iPhone_11")
					.quantity(4)
					.build();
			Inventory inventory2 = Inventory.builder()
					.skuCode("Tecno_Camon")
					.quantity(3)
					.build();
			Inventory inventory3 = Inventory.builder()
					.skuCode("Samsung_B")
					.quantity(0)
					.build();
			Inventory inventory4 = Inventory.builder()
					.skuCode("Pixel_7")
					.quantity(1)
					.build();
			repository.save(inventory1);
			repository.save(inventory2);
			repository.save(inventory3);
			repository.save(inventory4);
		};
	}
}
