package com.example.NotifyTrafficTelegramBot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationDto {

    @JsonProperty("telegramUserId")
    private String telegramUserId;

    @JsonProperty("arrivalTime")
    private String arrivalTime;

    @JsonProperty("homeAddress")
    private String homeAddress;

    @JsonProperty("workAddress")
    private String workAddress;

    @JsonProperty("timeZone")
    private String timezone;
}

