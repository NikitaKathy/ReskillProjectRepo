package com.oms.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMsApplication.class, args);
	}

}