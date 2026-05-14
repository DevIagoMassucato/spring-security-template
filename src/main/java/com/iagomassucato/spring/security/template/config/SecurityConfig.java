package com.iagomassucato.spring.security.template.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/animes").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/animes/**").hasRole("USER")
                .antMatchers(HttpMethod.PATCH, "/api/v1/animes/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.DELETE, "/api/v1/animes/**").hasRole("ADMIN")
                .anyRequest().denyAll()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}123")
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("{noop}123")
                .roles("USER");
    }

}
