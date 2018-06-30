package com.xyz.sa2018;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;

//@EnableConfigServer
@EnableEurekaClient
@SpringBootApplication
//@EnableConfigurationProperties(StorageProperties.class)
public class DataNodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataNodeApplication.class, args);
		//System.out.println("enter datanode");//
	}
}
