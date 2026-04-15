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
                .authorizeRequests()// //autorizar todas
                    .antMatchers("/api/v1/anime/public/**").permitAll()// end-point público
                    .antMatchers(HttpMethod.POST, "/api/v1/anime").hasRole("ADMIN")// aplica autenticação nesse método http, precisa do role admin
                    .antMatchers(HttpMethod.GET, "/api/v1/anime").authenticated()// aplica autenticação, qualquer usuário autenticado
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
