package com.example.NotifyTrafficTelegramBot.service.serviceUtils;

import com.example.NotifyTrafficTelegramBot.dto.UserInformationDto;
import com.example.NotifyTrafficTelegramBot.dto.UserSessionDto;
import com.example.NotifyTrafficTelegramBot.enums.States;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionStorage {

    private final Map<Long, UserSessionDto> storage = new ConcurrentHashMap<>();

    public void startSession(Long chatId) {
        UserSessionDto session = new UserSessionDto();
        session.setState(States.ASK_ARRIVAL_DATE);
        session.setInformationDto(new UserInformationDto());
        storage.put(chatId, session);
    }

    public void endSession(Long chatId) {
        storage.remove(chatId);
    }

    public UserSessionDto getSession(Long chatId) {
        return storage.get(chatId);
    }

    public Map<Long, UserSessionDto> getAll() {
        return storage;
    }
}