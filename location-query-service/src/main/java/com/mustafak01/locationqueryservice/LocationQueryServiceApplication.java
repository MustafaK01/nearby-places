package com.mustafak01.locationqueryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LocationQueryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocationQueryServiceApplication.class, args);
	}

}
