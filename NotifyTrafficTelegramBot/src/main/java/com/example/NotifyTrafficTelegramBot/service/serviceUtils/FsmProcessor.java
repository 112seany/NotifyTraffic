package com.example.NotifyTrafficTelegramBot.service.serviceUtils;

import com.example.NotifyTrafficTelegramBot.dto.UserInformationDto;
import com.example.NotifyTrafficTelegramBot.dto.UserSessionDto;
import com.example.NotifyTrafficTelegramBot.enums.States;
import com.example.NotifyTrafficTelegramBot.gateway.NotifyIntegrationGateway;
import com.example.NotifyTrafficTelegramBot.validation.UserRequestValidator;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Consumer;

@Component
@Getter
public class FsmProcessor {

    @Autowired
    private NotifyIntegrationGateway NotifyIntegrationGateway;

    @Autowired
    private SessionStorage sessionStorage;

    public boolean handleFsmInput(Long chatId, String message, Consumer<String> replySender) {
        UserSessionDto session = sessionStorage.getSession(chatId);
        if (session == null || session.getState() == null) {
            replySender.accept("🪧 Пожалуйста, нажмите кнопку \"Начать настройку\".");
            return false;
        }

        switch (session.getState()) {
            case ASK_ARRIVAL_DATE -> {
                if (!UserRequestValidator.isValidArrivalTime(message)) {
                    replySender.accept("❗ Введите время в формате HH:mm (например, 08:30)");
                    return true;
                }
                session.getInformationDto().setArrivalTime(message);
                session.setState(States.ASK_HOME_ADDRESS);
                session.getInformationDto().setTelegramUserId(chatId.toString());
                replySender.accept("🏠 Введите домашний адрес:");
            }
            case ASK_HOME_ADDRESS -> {
                if(Objects.isNull(message) || message.isBlank()) {
                    replySender.accept("❗ Введите непустой домашний адрес");
                    return true;
                }
                session.getInformationDto().setHomeAddress(message);
                session.setState(States.ASK_WORK_ADDRESS);
                replySender.accept("🏢 Введите адрес работы:");
            }
            case ASK_WORK_ADDRESS -> {
                if(Objects.isNull(message) || message.isBlank()) {
                    replySender.accept("❗ Введите непустой рабочий адрес");
                    return true;
                }
                session.getInformationDto().setWorkAddress(message);
                session.setState(States.ASK_TIMEZONE);
                replySender.accept("🌍 Укажите часовой пояс (например, UTC+3):");
            }
            case ASK_TIMEZONE -> {
                if (!UserRequestValidator.isValidTimezone(message)) {
                    replySender.accept("❗ Укажите часовой пояс в формате UTC±N (например, UTC+3)");
                    return true;
                }
                session.getInformationDto().setTimezone(message);
                session.setState(States.DONE);
                NotifyIntegrationGateway.sendUserInformation(session.getInformationDto());
                replySender.accept("✅ Все данные получены:\n\n" + format(session.getInformationDto()));
            }
            case DONE -> {
                replySender.accept("👋 Настройка завершена. Напишите /start для новой.");
            }
        }
        return true;
    }

    private String format(UserInformationDto data) {
        return "📅 Дата прибытия: " + data.getArrivalTime() + "\n" +
                "🏠 Домашний адрес: " + data.getHomeAddress() + "\n" +
                "🏢 Адрес работы: " + data.getWorkAddress() + "\n" +
                "🕒 Часовой пояс: " + data.getTimezone();
    }

}
