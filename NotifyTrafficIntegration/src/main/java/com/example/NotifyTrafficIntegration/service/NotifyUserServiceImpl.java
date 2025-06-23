package com.example.NotifyTrafficIntegration.service;

import com.example.NotifyTrafficIntegration.api.GeoDataClient;
import com.example.NotifyTrafficIntegration.api.GoogleMapApiClient;
import com.example.NotifyTrafficIntegration.api.TelegramNotificationApi;
import com.example.NotifyTrafficIntegration.dto.GeocodingResponseDto;
import com.example.NotifyTrafficIntegration.dto.NotifyRequest;
import com.example.NotifyTrafficIntegration.dto.NotifyResponse;
import com.example.NotifyTrafficIntegration.mapper.NotifyMapper;
import com.example.NotifyTrafficIntegration.repository.NotifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotifyUserServiceImpl implements NotifyUserService {

    @Value("${google.maps.token}")
    private String apiKey;

    @Autowired
    private GeoDataClient geoDataClient;

    @Autowired
    private NotifyRepository notifyRepository;

    @Autowired
    private NotifyMapper notifyMapper;

    @Override
    public NotifyResponse setNotification(NotifyRequest notifyRequest) {
        GeocodingResponseDto homeAddressCoordinates = geoDataClient.getCoordinates(notifyRequest.getHomeAddress(), apiKey);
        GeocodingResponseDto workAddressCoordinates = geoDataClient.getCoordinates(notifyRequest.getWorkAddress(), apiKey);

        notifyRepository.save(notifyMapper.mapGeocodingResponseDtoToUserSettingsEntity(homeAddressCoordinates, workAddressCoordinates, notifyRequest));

        return new NotifyResponse("Уведомление на " + notifyRequest.getArrivalTime() + " успешно установлено");
    }

}
