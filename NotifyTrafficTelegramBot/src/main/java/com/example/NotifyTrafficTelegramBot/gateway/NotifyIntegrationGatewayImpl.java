package com.example.NotifyTrafficTelegramBot.gateway;

import com.example.NotifyTrafficTelegramBot.dto.UserInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Component
public class NotifyIntegrationGatewayImpl implements NotifyIntegrationGateway {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${notify.service.url}")
    private String notifyServiceUrl;

    @Override
    public void sendUserInformation(UserInformationDto dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserInformationDto> request = new HttpEntity<>(dto, headers);

        try {
            restTemplate.postForEntity(notifyServiceUrl, request, Void.class);
        } catch (RestClientException e) {
            // лог или алерт
            System.err.println("Ошибка при отправке в NotifyIntegration: " + e.getMessage());
        }
    }
}
