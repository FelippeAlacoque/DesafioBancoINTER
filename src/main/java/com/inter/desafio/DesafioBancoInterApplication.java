package com.inter.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DesafioBancoInterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioBancoInterApplication.class, args);
	}

}
