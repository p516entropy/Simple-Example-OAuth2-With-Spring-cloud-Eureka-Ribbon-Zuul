package com.lungesoft.architecture.core.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableWebSecurity
@EnableResourceServer
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/webjars/**",
                        "/swagger-ui.html",
                        "/swagger",
                        "/swagger-resources/**",
                        "/v2/api-docs").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }

    //TODO replace to JWT VALIDATION
    @Bean
    public RemoteTokenServices remoteTokenServices(
            final @Value("${auth.server.url}") String checkTokenUrl,
            final @Value("${auth.server.clientId}") String clientId,
            final @Value("${auth.server.clientsecret}") String clientSecret) {
        final RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl(checkTokenUrl);
        remoteTokenServices.setClientId(clientId);
        remoteTokenServices.setClientSecret(clientSecret);
        remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
        return remoteTokenServices;
    }

}