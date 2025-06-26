package com.example.NotifyTrafficIntegration.service;

import com.example.NotifyTrafficIntegration.api.GeoDataClient;
import com.example.NotifyTrafficIntegration.api.GoogleMapApiClient;
import com.example.NotifyTrafficIntegration.api.TelegramNotificationApi;
import com.example.NotifyTrafficIntegration.dto.*;
import com.example.NotifyTrafficIntegration.entity.UserSettingsEntity;
import com.example.NotifyTrafficIntegration.mapper.NotifyMapper;
import com.example.NotifyTrafficIntegration.repository.NotifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
public class NotifyUserServiceImpl implements NotifyUserService {

    @Value("${google.maps.token}")
    private String apiKey;

    @Autowired
    private GoogleMapApiClient googleMapApiClient;

    @Autowired
    private TelegramNotificationApi telegramNotificationGateway;

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

    @Override
    public void processNotificationCycle() {
        ZonedDateTime nowUtc = ZonedDateTime.now(ZoneOffset.UTC);
        LocalTime windowStart = nowUtc.toLocalTime();
        LocalTime windowEnd = windowStart.plusHours(2);

        List<UserSettingsEntity> users = notifyRepository.findUsersToNotify(windowStart, windowEnd);

        for (UserSettingsEntity user : users) {
            LocalTime arrivalTime = user.getArrivalTime();
            ZonedDateTime userArrivalUtc = LocalDate.now(ZoneOffset.UTC)
                    .atTime(arrivalTime)
                    .atZone(ZoneOffset.UTC);

            DistanceMatrixResponseDto responseDto = googleMapApiClient.getDurationWithTraffic(
                    notifyMapper.mapUserSettingsEntityToDistanceMatrixRequestDto(user),
                    apiKey
            );

            long travelSeconds = responseDto.getRows().getFirst()
                    .getElements().getFirst()
                    .getDurationInTraffic().getValue();

            Duration travelDuration = Duration.ofSeconds(travelSeconds);
            ZonedDateTime departureUtc = userArrivalUtc.minus(travelDuration);
            ZonedDateTime notificationUtc = departureUtc.minusMinutes(30);

            if (nowUtc.isAfter(notificationUtc.minusMinutes(5)) && nowUtc.isBefore(notificationUtc.plusMinutes(5))) {
                telegramNotificationGateway.notifyUser(
                        new TelegramNotifyResponse(user.getTelegramUserId(), "Пора выезжать")
                );
                user.setLastNotified(nowUtc);
                notifyRepository.save(user);
            }
        }
    }
}
