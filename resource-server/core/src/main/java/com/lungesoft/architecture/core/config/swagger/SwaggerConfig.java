package com.lungesoft.architecture.core.config.swagger;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.Principal;
import java.util.Collections;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(Principal.class)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(
                        new ApiKey(
                                "Authorization: Bearer token",
                                "Authorization",
                                "header")))
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("REST API doc")
                .description("REST API description")
                .version("0.0.1")
                .build();
    }

}
