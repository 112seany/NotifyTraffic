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
public class ElementsDto {

    @JsonProperty("duration_in_traffic")
    private DurationValueDto durationInTraffic;
}
