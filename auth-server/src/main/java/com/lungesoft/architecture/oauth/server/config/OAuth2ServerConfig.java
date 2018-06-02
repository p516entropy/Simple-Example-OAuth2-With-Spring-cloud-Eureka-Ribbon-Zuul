package com.lungesoft.architecture.oauth.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@EnableAuthorizationServer
@Configuration
public class OAuth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${oauth2.jwt-secret-key}")
    private String jwtSecretKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.withClientDetails(clientId -> {
            //CAN-TODO here you can customize your oauth2 clients managing
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
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
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
        //CAN-TODO write your own UserAuthenticationConverter.convertUserAuthentication
        //to convert Authentication object with custom Principle to Json Web Token.
        final UserAuthenticationConverter authenticationConverter = new DefaultUserAuthenticationConverter();

        final DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        defaultAccessTokenConverter.setUserTokenConverter(authenticationConverter);

        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(jwtSecretKey);
        return converter;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer serverEndpointsConfigurer) {
        serverEndpointsConfigurer
                .authenticationManager(authenticationManager)
                .accessTokenConverter(accessTokenConverter());
    }
}
