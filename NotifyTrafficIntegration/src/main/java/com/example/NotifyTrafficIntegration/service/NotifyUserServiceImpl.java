package com.example.NotifyTrafficIntegration.service;

import com.example.NotifyTrafficIntegration.dto.DistanceMatrixResponseDto;
import com.example.NotifyTrafficIntegration.dto.GeocodingResponseDto;
import com.example.NotifyTrafficIntegration.entity.UserSettingsEntity;
import com.example.NotifyTrafficIntegration.gateway.CoordinatesGateway;
import com.example.NotifyTrafficIntegration.gateway.TelegramNotificationGateway;
import com.example.NotifyTrafficIntegration.gateway.TravelDurationGateway;
import com.example.NotifyTrafficIntegration.mapper.NotifyMapper;
import com.example.NotifyTrafficIntegration.repository.NotifyRepository;
import com.example.NotifyTrafficIntegration.rest.request.NotifyRequest;
import com.example.NotifyTrafficIntegration.rest.response.TelegramNotifyResponse;
import com.example.NotifyTrafficIntegration.rest.response.NotifyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
public class NotifyUserServiceImpl implements NotifyUserService {

    @Autowired
    private CoordinatesGateway getCoordinatesGateway;

    @Autowired
    private NotifyRepository notifyRepository;

    @Autowired
    private TravelDurationGateway travelDurationGateway;

    @Autowired
    private NotifyMapper notifyMapper;

    @Autowired
    private TelegramNotificationGateway telegramNotificationGateway;

    @Override
    public NotifyResponse setNotification(NotifyRequest notifyRequest) {
        GeocodingResponseDto homeAddressCoordinates = getCoordinatesGateway.getCoordinates(notifyRequest.getHomeAddress());
        GeocodingResponseDto workAddressCoordinates = getCoordinatesGateway.getCoordinates(notifyRequest.getWorkAddress());

        notifyRepository.save(notifyMapper.mapGeocodingResponseDtoToUserSettingsEntity(homeAddressCoordinates, workAddressCoordinates, notifyRequest));

        return new NotifyResponse("Уведомление на " + notifyRequest.getArrivalTime() + " успешно установлено");
    }

    @Scheduled(cron = "*/5 0-59 4-18 * * MON-FRI", zone = "UTC")
    public void scheduleUserNotifications() {
        ZonedDateTime nowUtc = ZonedDateTime.now(ZoneOffset.UTC);
        LocalTime currentTime = nowUtc.toLocalTime();
        LocalTime maxTime = currentTime.plusHours(2);

        List<UserSettingsEntity> users = notifyRepository.findUsersToNotify(currentTime, maxTime);

        for (UserSettingsEntity user : users) {
            LocalTime arrivalTime = user.getArrivalTime();
            ZonedDateTime userArrivalUtc = LocalDate.now(ZoneOffset.UTC)
                    .atTime(arrivalTime)
                    .atZone(ZoneOffset.UTC);


            DistanceMatrixResponseDto responseDto = travelDurationGateway.getDurationWithTraffic(notifyMapper.mapUserSettingsEntityToDistanceMatrixRequestDto(user));

            long travelSeconds = responseDto.getRows().getFirst()
                    .getElements().getFirst()
                    .getDurationInTraffic().getValue();

            Duration travelDuration = Duration.ofSeconds(travelSeconds);

            ZonedDateTime departureUtc = userArrivalUtc.minus(travelDuration);

            ZonedDateTime notificationUtc = departureUtc.minusMinutes(30);

            if (nowUtc.isAfter(notificationUtc.minusMinutes(5)) && nowUtc.isBefore(notificationUtc.plusMinutes(5))) {
                telegramNotificationGateway.notifyUser(new TelegramNotifyResponse(user.getTelegramUserId(),"Пора выезжать"));

                user.setLastNotified(nowUtc);
                notifyRepository.save(user);
            }
        }
    }
}
