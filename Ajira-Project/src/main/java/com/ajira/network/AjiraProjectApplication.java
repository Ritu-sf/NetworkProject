package com.ajira.network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.ajira")
@SpringBootApplication
public class AjiraProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AjiraProjectApplication.class, args);
	}

}
