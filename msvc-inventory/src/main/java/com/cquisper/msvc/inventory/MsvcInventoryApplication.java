package com.cquisper.msvc.inventory;

import com.cquisper.msvc.inventory.models.Inventory;
import com.cquisper.msvc.inventory.repositories.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsvcInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcInventoryApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadDataTest(InventoryRepository inventoryRepository) {
		return (args) -> {
			Inventory inventoryProduct1 = Inventory.builder()
					.productCode("iphone_13")
					.stock(100)
					.build();

			Inventory inventoryProduct2 = Inventory.builder()
					.productCode("iphone_13_red")
					.stock(0)
					.build();

			inventoryRepository.save(inventoryProduct1);
			inventoryRepository.save(inventoryProduct2);
		};
	}

}
