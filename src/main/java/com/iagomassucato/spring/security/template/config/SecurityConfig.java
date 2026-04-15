package com.iagomassucato.spring.security.template.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // necessário para API
                .authorizeRequests() //autorizar todas
                .anyRequest().authenticated() // qualquer requisição autenticado - tudo precisa de login
                .and()
                .httpBasic(); // usa Basic Auth
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}123") // {noop} não faça nenhum tipo de criptografia na senha - Password Encoder
                .roles("USER");
    }

}
