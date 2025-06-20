package com.example.NotifyTrafficIntegration.gateway;

import com.example.NotifyTrafficIntegration.dto.DistanceMatrixRequestDto;
import com.example.NotifyTrafficIntegration.dto.DistanceMatrixResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class TravelDurationGatewayImpl implements TravelDurationGateway {

    @Value("${google.maps.token}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public DistanceMatrixResponseDto getDurationWithTraffic(DistanceMatrixRequestDto request) {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins={origins}&mode=driving&departure_time=now"+
                "&traffic_model=best_guess&key={key}&destinations={destinations}";
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("origins", request.getOrigins());
        queryParams.put("destinations", request.getDestinations());
        queryParams.put("key", apiKey);

        return restTemplate.getForObject(url, DistanceMatrixResponseDto.class, queryParams);
    }
}
