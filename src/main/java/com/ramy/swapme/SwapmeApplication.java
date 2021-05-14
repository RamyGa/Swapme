package com.ramy.swapme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableCaching
public class SwapmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwapmeApplication.class, args);
	}

	// TODO * Configuring this bean should not be needed once Spring Boot's Thymeleaf starter includes configuration
	// TODO for thymeleaf-extras-springsecurity5 (instead of thymeleaf-extras-springsecurity4)



}
