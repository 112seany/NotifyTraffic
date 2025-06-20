package com.example.NotifyTrafficIntegration.gateway;

import com.example.NotifyTrafficIntegration.dto.GeocodingResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class CoordinatesGatewayImpl implements CoordinatesGateway {

    @Value("${google.maps.token}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GeocodingResponseDto getCoordinates(String address) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address={address}&key={key}";

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("address", address);
        queryParams.put("key", apiKey);

        return restTemplate.getForObject(url, GeocodingResponseDto.class, queryParams);

    }
}
