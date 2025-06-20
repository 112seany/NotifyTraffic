package com.example.NotifyTrafficIntegration.gateway;

import com.example.NotifyTrafficIntegration.dto.DistanceMatrixRequestDto;
import com.example.NotifyTrafficIntegration.dto.DistanceMatrixResponseDto;

public interface TravelDurationGateway {

     DistanceMatrixResponseDto getDurationWithTraffic(DistanceMatrixRequestDto request);

}
