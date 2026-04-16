package com.iagomassucato.spring.security.template.config;

import com.iagomassucato.spring.security.template.user.UserDetailsServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImp userDetailsServiceImp;
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()// //autorizar todas
                    .antMatchers("/api/v1/anime/public/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/anime").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/api/v1/anime/**").authenticated()
                    .antMatchers(HttpMethod.DELETE, "/api/v1/anime/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PATCH, "/api/v1/anime/**").hasRole("ADMIN")
                    .anyRequest().denyAll()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsServiceImp)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
