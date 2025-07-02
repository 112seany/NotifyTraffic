package com.example.NotifyTrafficIntegration.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotifyRequest {

    @JsonProperty("telegramUserId")
    private String telegramUserId;

    @JsonProperty("homeAddress")
    private String homeAddress;

    @JsonProperty("workAddress")
    private String workAddress;

    @JsonProperty("arrivalTime")
    private String arrivalTime;

    @JsonProperty("timeZone")
    private String timeZone;
}
