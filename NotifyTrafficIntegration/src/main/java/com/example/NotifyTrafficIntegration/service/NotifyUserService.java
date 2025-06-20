package com.example.NotifyTrafficIntegration.service;

import com.example.NotifyTrafficIntegration.rest.request.NotifyRequest;
import com.example.NotifyTrafficIntegration.rest.response.NotifyResponse;

public interface NotifyUserService {

    NotifyResponse setNotification(NotifyRequest notifyRequest);
}
