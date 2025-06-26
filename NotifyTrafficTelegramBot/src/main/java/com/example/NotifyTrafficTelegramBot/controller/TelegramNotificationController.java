package com.example.NotifyTrafficTelegramBot.controller;

import com.example.NotifyTrafficTelegramBot.dto.TelegramNotificationRequest;
import com.example.NotifyTrafficTelegramBot.service.NotifyTelegramBot;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telegram/api")
@AllArgsConstructor
public class TelegramNotificationController {

    private final NotifyTelegramBot notifyTelegramBot;

    @PostMapping("/notification")
    public ResponseEntity<Void> notify(@RequestBody TelegramNotificationRequest request) {
        notifyTelegramBot.sendMessage(request.getTelegramUserId(), request.getMessage());
        return ResponseEntity.ok().build();
    }
}
