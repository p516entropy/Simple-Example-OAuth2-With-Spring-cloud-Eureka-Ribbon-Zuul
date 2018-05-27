package com.lungesoft.architecture.core.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Api(description = "Principal description")
@RestController
public class PrincipalController {

    @GetMapping("/rest/me")
    public Principal getUser(Principal principal) {
        return principal;
    }

}
