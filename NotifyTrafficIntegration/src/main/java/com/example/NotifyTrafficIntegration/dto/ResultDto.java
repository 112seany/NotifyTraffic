package com.example.NotifyTrafficIntegration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {

    @JsonProperty("navigation_points")
    private List<NavigationPointDto> navigationPoints;
}
