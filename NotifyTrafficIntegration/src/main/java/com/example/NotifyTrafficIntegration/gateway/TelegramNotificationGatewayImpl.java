package com.example.NotifyTrafficIntegration.gateway;

import com.example.NotifyTrafficIntegration.rest.response.TelegramNotifyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TelegramNotificationGatewayImpl implements TelegramNotificationGateway {

    @Autowired
    private RestTemplate restTemplate;

    private final String telegramServiceUrl;

    public TelegramNotificationGatewayImpl(@Value("${telegram.service.url}") String telegramServiceUrl) {
        this.telegramServiceUrl = telegramServiceUrl;
    }

    @Override
    public void notifyUser(TelegramNotifyResponse telegramNotifyRequest) {
        restTemplate.postForEntity(
                telegramServiceUrl,
                telegramNotifyRequest,
                Void.class
        );
    }
}
