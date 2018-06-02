package com.lungesoft.architecture.oauth.server.config;

import com.lungesoft.architecture.oauth.server.model.User;
import com.lungesoft.architecture.oauth.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UsernamePasswordCustomAuthentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    public void init(AuthenticationManagerBuilder auth) {

        auth.authenticationProvider(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String login = authentication.getName();
                String password = authentication.getCredentials().toString();
                User user = userService.retrieveUserByUsername(login);

                //TODO use hash algorithm for password
                if (user != null && user.getPassword().compareTo(password) == 0) {
                    List<GrantedAuthority> grantedAuths = new ArrayList<>();
                    grantedAuths.add(new SimpleGrantedAuthority(user.getRole()));
                    return new UsernamePasswordAuthenticationToken(user, password, grantedAuths);
                } else {
                    throw new BadCredentialsException("invalid username or password");
                }
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.equals(UsernamePasswordAuthenticationToken.class);
            }
        });
    }
}