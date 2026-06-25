package com.iagomassucato.spring.security.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringSecurityTemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityTemplateApplication.class, args);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println("PASSWORD => "+bCryptPasswordEncoder.encode("123"));
    }
}
