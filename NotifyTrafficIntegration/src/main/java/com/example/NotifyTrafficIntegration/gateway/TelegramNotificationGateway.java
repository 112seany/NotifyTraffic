package com.example.NotifyTrafficIntegration.gateway;

import com.example.NotifyTrafficIntegration.rest.response.TelegramNotifyResponse;

public interface TelegramNotificationGateway {

    void notifyUser(TelegramNotifyResponse telegramNotifyRequest);
}
