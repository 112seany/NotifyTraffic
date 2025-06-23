package com.example.NotifyTrafficIntegration.api;

import com.example.NotifyTrafficIntegration.dto.TelegramNotifyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(
        name = "telegramNotificationClient",
        url = "${telegram.service.url}"
)
public interface TelegramNotificationApi {

    @PostMapping
    void notifyUser(@RequestBody TelegramNotifyResponse telegramNotifyRequest);
}
