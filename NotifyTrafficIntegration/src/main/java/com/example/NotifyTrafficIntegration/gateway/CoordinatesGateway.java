package com.example.NotifyTrafficIntegration.gateway;

import com.example.NotifyTrafficIntegration.dto.GeocodingResponseDto;

public interface CoordinatesGateway {

    GeocodingResponseDto getCoordinates(String address);
}
