package com.example.NotifyTrafficTelegramBot.gateway;

import com.example.NotifyTrafficTelegramBot.dto.UserInformationDto;

public interface NotifyIntegrationGateway {

     void sendUserInformation(UserInformationDto dto);
}
