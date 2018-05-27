package com.lungesoft.architecture.oauth.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@EnableAuthorizationServer
@Configuration
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.withClientDetails(clientId -> {
            final String clientIdMock = "clientIdMock";
            final String clientSecretMock = "clientSecretMock";
            final Collection<String> scopeMock = Collections.singleton("auth");
            final Collection<String> grantTypesMock = Arrays.asList("authorization_code", "refresh_token");
            if (!clientId.equals(clientIdMock)) {
                return null;
            }
            final BaseClientDetails clientDetails = new BaseClientDetails();
            clientDetails.setClientId(clientIdMock);
            clientDetails.setClientSecret(clientSecretMock);
            clientDetails.setAuthorizedGrantTypes(grantTypesMock);
            clientDetails.setScope(scopeMock);
            clientDetails.setAutoApproveScopes(scopeMock);
            return clientDetails;
        });
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer)
            throws Exception {
        oauthServer
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter());
    }
}
