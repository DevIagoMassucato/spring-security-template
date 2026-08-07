package com.iagomassucato.spring.security.template.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NotificationClient {

    private final RestTemplate restTemplate;

    @Value("${notification.url}")
    private String notificationUrl;

    public void sendEmail(String email, String subject, String body) {
        EmailRequest emailRequest = new EmailRequest(
                email,
                subject,
                body
        );
        restTemplate.postForEntity(
                notificationUrl + "/api/v1/emails",
                emailRequest,
                Void.class
        );
    }
}
