package com.iagomassucato.spring.security.template.client;

public record EmailRequest(
        String emailAddress,
        String subject,
        String body
) {
}
