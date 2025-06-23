package com.example.NotifyTrafficIntegration.controller;

import com.example.NotifyTrafficIntegration.dto.NotifyRequest;
import com.example.NotifyTrafficIntegration.dto.NotifyResponse;
import com.example.NotifyTrafficIntegration.service.NotifyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class NotifyController {

    @Autowired
    private  NotifyUserService notifyUserService;

    @PostMapping
    public ResponseEntity<NotifyResponse> setNotification(@RequestBody NotifyRequest address) {
        return ResponseEntity.ok(notifyUserService.setNotification(address));
    }

}
