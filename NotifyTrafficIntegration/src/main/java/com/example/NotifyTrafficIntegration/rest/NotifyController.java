package com.example.NotifyTrafficIntegration.rest;

import com.example.NotifyTrafficIntegration.rest.request.NotifyRequest;
import com.example.NotifyTrafficIntegration.rest.response.NotifyResponse;
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
