package com.example.NotifyTrafficIntegration.api;

import com.example.NotifyTrafficIntegration.dto.DistanceMatrixRequestDto;
import com.example.NotifyTrafficIntegration.dto.DistanceMatrixResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class GoogleMapApiClientImpl implements GoogleMapApiClient {

    @Value("${google.maps.token}")
    private String apiKey;

    @Value("${google.maps.distance-matrix-url}")
    private String distanceMatrixUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public DistanceMatrixResponseDto getDurationWithTraffic(DistanceMatrixRequestDto request) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("origins", request.getOrigins());
        queryParams.put("destinations", request.getDestinations());
        queryParams.put("key", apiKey);

        return restTemplate.getForObject(distanceMatrixUrl, DistanceMatrixResponseDto.class, queryParams);
    }
}
