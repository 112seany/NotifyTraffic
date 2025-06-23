package com.example.NotifyTrafficIntegration.api;

import com.example.NotifyTrafficIntegration.dto.DistanceMatrixRequestDto;
import com.example.NotifyTrafficIntegration.dto.DistanceMatrixResponseDto;

public interface GoogleMapApiClient {

     DistanceMatrixResponseDto getDurationWithTraffic(DistanceMatrixRequestDto request);

}
