package com.example.NotifyTrafficTelegramBot.rest;

import com.example.NotifyTrafficTelegramBot.rest.requests.TelegramNotificationRequest;
import com.example.NotifyTrafficTelegramBot.service.NotifyTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telegram/api")
public class TelegramNotificationController {

    @Autowired
    private NotifyTelegramBot notifyTelegramBot;

    @PostMapping("/notification")
    public ResponseEntity<Void> notify(@RequestBody TelegramNotificationRequest request) {
        notifyTelegramBot.sendMessage(request.getTelegramUserId(), request.getMessage());
        return ResponseEntity.ok().build();
    }
}
