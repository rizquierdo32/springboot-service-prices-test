package com.rizquierdo.servicepricestest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.rizquierdo.servicepricestest")
public class ServicePricesTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicePricesTestApplication.class, args);
	}

}
