package com.example.NotifyTrafficTelegramBot.api;

import com.example.NotifyTrafficTelegramBot.dto.UserInformationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "notifyIntegrationClient",
        url = "${notify.service.url}"
)
public interface NotifyIntegrationApi {

     @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
     void sendUserInformation(@RequestBody UserInformationDto dto);
}