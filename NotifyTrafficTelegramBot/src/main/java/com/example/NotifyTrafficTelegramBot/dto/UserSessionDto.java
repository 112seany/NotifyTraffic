package com.example.NotifyTrafficTelegramBot.dto;

import com.example.NotifyTrafficTelegramBot.enums.States;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionDto {
    private States state = States.ASK_ARRIVAL_DATE;
    private UserInformationDto informationDto = new UserInformationDto();
}
