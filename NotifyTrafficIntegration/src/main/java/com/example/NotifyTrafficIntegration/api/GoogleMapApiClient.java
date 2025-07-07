package com.example.NotifyTrafficIntegration.api;

import com.example.NotifyTrafficIntegration.dto.DistanceMatrixRequestDto;
import com.example.NotifyTrafficIntegration.dto.DistanceMatrixResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "googleMapClient",
        url = "${google.maps.url}"
)
public interface GoogleMapApiClient {

     @GetMapping("/distancematrix/json")
     DistanceMatrixResponseDto getDurationWithTraffic(@RequestParam("origins") String origins,
                                                      @RequestParam("destinations") String destinations,
                                                      @RequestParam("key") String apiKey);
}