package com.example.NotifyTrafficIntegration.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistanceMatrixRequestDto {

    private String origins;

    private String destinations;
}
