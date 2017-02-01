package com.queen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

import java.io.IOException;

@SpringBootApplication
@EnableIntegration
public class SpringsandboxApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(SpringsandboxApplication.class, args);
	}
}
