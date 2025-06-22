package com.example.NotifyTrafficIntegration.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelegramNotifyResponse {

    @JsonProperty("telegramUserId")
    private Long telegramUserId;

    @JsonProperty("message")
    private String message;
}
