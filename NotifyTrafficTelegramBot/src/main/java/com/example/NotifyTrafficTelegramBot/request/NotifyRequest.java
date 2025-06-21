package com.example.NotifyTrafficTelegramBot.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyRequest {
    private Long telegramUserId;
    private String arrivalTime;
    private String homeAddress;
    private String workAddress;
    private String timeZone;
}