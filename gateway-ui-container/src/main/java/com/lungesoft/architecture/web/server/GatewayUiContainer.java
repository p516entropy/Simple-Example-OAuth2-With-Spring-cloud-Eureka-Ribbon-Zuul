package com.lungesoft.architecture.web.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class GatewayUiContainer {

    public static void main(String[] args) {
        SpringApplication.run(GatewayUiContainer.class, args);
    }

}
