package com.lungesoft.architecture.core.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

//CAN-TODO you can use RemoteTokenServices in case if you not use JWT
//@Configuration
public class RemoteTokenServiceBeanConfig {

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }

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
