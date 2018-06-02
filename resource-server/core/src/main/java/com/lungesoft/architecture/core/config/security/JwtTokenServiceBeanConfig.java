package com.lungesoft.architecture.core.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class JwtTokenServiceBeanConfig {

    @Value("${oauth2.jwt-secret-key}")
    private String jwtSecretKey;

    @Bean
    public DefaultTokenServices defaultTokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        //CAN-TODO write your own UserAuthenticationConverter.extractAuthentication
        //to create Authentication object with custom Principle from Json Web Token.
        final UserAuthenticationConverter authenticationConverter = new DefaultUserAuthenticationConverter();

        final DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        defaultAccessTokenConverter.setUserTokenConverter(authenticationConverter);

        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtSecretKey);
        return converter;
    }

}
