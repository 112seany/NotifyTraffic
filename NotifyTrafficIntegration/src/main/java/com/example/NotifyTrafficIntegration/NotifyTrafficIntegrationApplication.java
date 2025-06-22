package com.example.NotifyTrafficIntegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NotifyTrafficIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotifyTrafficIntegrationApplication.class, args);
	}
}
