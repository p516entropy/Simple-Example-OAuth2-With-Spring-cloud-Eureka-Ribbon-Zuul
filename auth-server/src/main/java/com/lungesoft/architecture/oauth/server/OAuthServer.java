package com.lungesoft.architecture.oauth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@SpringBootApplication
@Controller
@EnableDiscoveryClient
public class OAuthServer {

    public static void main(final String[] args) {
        SpringApplication.run(OAuthServer.class, args);
    }

    @RequestMapping("/user")
    @ResponseBody
    public Principal user(Principal user) {
        return user;
    }
}
