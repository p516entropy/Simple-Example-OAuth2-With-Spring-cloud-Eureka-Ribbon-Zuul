package com.lungesoft.architecture.oauth.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    CAN-TODO use UserDetailsService if you want to use maximum spring solutions
//    CAN-TODO use UsernamePasswordCustomAuthentication if you want to totally customize Principle
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails userOne = User.withUsername("user")
//                .password("user")
//                .roles("USER")
//                .build();
//        UserDetails userTwo = User.withUsername("admin")
//                .password("admin")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(Arrays.asList(userOne, userTwo));
//    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login").permitAll().and()
                .authorizeRequests()
                .antMatchers(
                        "/login",
                        "/oauth/authorize",
                        "/oauth/confirm_access",
                        "/webjars/**",
                        "/js/**").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
