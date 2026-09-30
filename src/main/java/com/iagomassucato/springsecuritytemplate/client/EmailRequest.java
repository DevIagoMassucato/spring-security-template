package com.iagomassucato.springsecuritytemplate.client;

public record EmailRequest(
        String emailAddress,
        String subject,
        String body
) {
}
