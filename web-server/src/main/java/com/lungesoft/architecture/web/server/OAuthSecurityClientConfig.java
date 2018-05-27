package com.lungesoft.architecture.web.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.Filter;

@Configuration
@EnableOAuth2Client
public class OAuthSecurityClientConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

    @Bean
    public OAuth2ClientContextFilter oAuth2ClientContextFilter() {
        return new OAuth2ClientContextFilter();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.logout().and().authorizeRequests()
                .antMatchers("/index.html",
                        "/home.html",
                        "/",
                        "/resource/rest/**",
                        "/oauth/**",
                        "/auth/**",
                        "/oauth").permitAll().anyRequest()
                .authenticated()
                .and()
                .addFilterAfter(oAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringAntMatchers("/auth/**");
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            if (authException != null) {
                response.sendRedirect(request.getContextPath() + "/oauth");
            }
        });
    }

    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter ssoFilter = new OAuth2ClientAuthenticationProcessingFilter("/oauth");
        OAuth2RestTemplate ssoTemplate = new OAuth2RestTemplate(sso(), oauth2ClientContext);
        ssoFilter.setRestTemplate(ssoTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(ssoResource().getUserInfoUri(), sso().getClientId());
        tokenServices.setRestTemplate(ssoTemplate);
        ssoFilter.setTokenServices(tokenServices);
        return ssoFilter;
    }

    @Bean
    @ConfigurationProperties("sso.client")
    public AuthorizationCodeResourceDetails sso() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("sso.resource")
    public ResourceServerProperties ssoResource() {
        return new ResourceServerProperties();
    }
}
