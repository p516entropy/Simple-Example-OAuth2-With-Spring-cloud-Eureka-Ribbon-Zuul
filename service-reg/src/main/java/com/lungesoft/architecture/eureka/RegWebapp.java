package com.lungesoft.architecture.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@RefreshScope
public class RegWebapp {

	public static void main(String[] args) {
		SpringApplication.run(RegWebapp.class, args);
	}

}
