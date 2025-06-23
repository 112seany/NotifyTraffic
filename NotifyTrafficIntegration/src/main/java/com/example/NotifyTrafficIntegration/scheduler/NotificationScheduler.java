package com.example.NotifyTrafficIntegration.scheduler;

import com.example.NotifyTrafficIntegration.api.GeoDataClient;
import com.example.NotifyTrafficIntegration.api.GoogleMapApiClient;
import com.example.NotifyTrafficIntegration.api.TelegramNotificationApi;
import com.example.NotifyTrafficIntegration.dto.DistanceMatrixResponseDto;
import com.example.NotifyTrafficIntegration.dto.TelegramNotifyResponse;
import com.example.NotifyTrafficIntegration.entity.UserSettingsEntity;
import com.example.NotifyTrafficIntegration.mapper.NotifyMapper;
import com.example.NotifyTrafficIntegration.repository.NotifyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.List;

@Component
public class NotificationScheduler {

    @Value("${google.maps.token}")
    private String apiKey;

    @Autowired
    private NotifyRepository notifyRepository;

    @Autowired
    private GoogleMapApiClient googleMapApiClient;

    @Autowired
    private NotifyMapper notifyMapper;

    @Autowired
    private TelegramNotificationApi telegramNotificationGateway;


    @Scheduled(cron = "*/5 0-59 4-18 * * MON-FRI", zone = "UTC")
    public void scheduleUserNotifications() {
        ZonedDateTime nowUtc = ZonedDateTime.now(ZoneOffset.UTC);
        LocalTime windowStart = nowUtc.toLocalTime();
        LocalTime windowEnd = windowStart.plusHours(2);

        List<UserSettingsEntity> users = notifyRepository.findUsersToNotify(windowStart, windowEnd);

        for (UserSettingsEntity user : users) {
            processUserNotification(user, nowUtc);
        }
    }

    private void processUserNotification(UserSettingsEntity user, ZonedDateTime nowUtc) {
        LocalTime arrivalTime = user.getArrivalTime();
        ZonedDateTime userArrivalUtc = LocalDate.now(ZoneOffset.UTC)
                .atTime(arrivalTime)
                .atZone(ZoneOffset.UTC);

        DistanceMatrixResponseDto responseDto =
                googleMapApiClient.getDurationWithTraffic(notifyMapper.mapUserSettingsEntityToDistanceMatrixRequestDto(user));

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
