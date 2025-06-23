package com.example.NotifyTrafficIntegration.service;

import com.example.NotifyTrafficIntegration.dto.NotifyRequest;
import com.example.NotifyTrafficIntegration.dto.NotifyResponse;

public interface NotifyUserService {

    NotifyResponse setNotification(NotifyRequest notifyRequest);
}
