package com.AbnerTest.ecommerce_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EcommerceTestApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcommerceTestApplication.class, args);
	}
}
