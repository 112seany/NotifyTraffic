package com.example.NotifyTrafficIntegration.api;

import com.example.NotifyTrafficIntegration.dto.GeocodingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "geoDataClient",
        url = "${google.maps.geocode-url}"
)
public interface GeoDataClient {

    @GetMapping
    GeocodingResponseDto getCoordinates(@RequestParam("address") String address,
                                        @RequestParam("key") String apiKey);
}
