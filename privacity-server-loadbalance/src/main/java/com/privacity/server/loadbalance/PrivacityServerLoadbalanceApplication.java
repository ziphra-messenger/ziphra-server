package com.privacity.server.loadbalance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.privacity.server.loadbalance")
public class PrivacityServerLoadbalanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrivacityServerLoadbalanceApplication.class, args);
	}

}
