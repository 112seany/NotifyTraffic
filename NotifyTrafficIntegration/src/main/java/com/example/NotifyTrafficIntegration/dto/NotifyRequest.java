package com.example.NotifyTrafficIntegration.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyRequest {

    private Long telegramUserId;
    private String homeAddress;
    private String workAddress;
    private String arrivalTime;
    private String timeZone;
}
