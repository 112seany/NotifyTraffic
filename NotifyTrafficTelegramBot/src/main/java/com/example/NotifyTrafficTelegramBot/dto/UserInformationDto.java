package com.example.NotifyTrafficTelegramBot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationDto {
    private String arrivalDate;
    private String homeAddress;
    private String workAddress;
    private String timezone;
}
