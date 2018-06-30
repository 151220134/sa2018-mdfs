package com.xyz.sa2018;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
//@EnableConfigurationProperties(StorageProperties.class)
public class NameNodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NameNodeApplication.class, args);
	}
}
