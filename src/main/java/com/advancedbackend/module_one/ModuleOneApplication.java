package com.advancedbackend.module_one;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ModuleOneApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ModuleOneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello world!");
	}
}
