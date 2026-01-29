package com.resilience.bulkhead;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BulkheadPatternApplication {

	public static void main(String[] args) {
		SpringApplication.run(BulkheadPatternApplication.class, args);
	}

}
