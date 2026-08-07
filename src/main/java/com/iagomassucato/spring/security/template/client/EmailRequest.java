package com.iagomassucato.spring.security.template.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmailRequest {
    private String emailAddress;
    private String subject;
    private String body;
}
