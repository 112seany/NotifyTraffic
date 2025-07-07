package com.example.NotifyTrafficIntegration.scheduler;

import com.example.NotifyTrafficIntegration.service.NotifyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    @Value("${google.maps.token}")
    private String apiKey;

    @Autowired
    private NotifyUserService notifyUserService;

    @Scheduled(cron = "*/5 * * * * MON-FRI", zone = "UTC")
    public void scheduleUserNotifications() {
        notifyUserService.processNotificationCycle();
    }
}
