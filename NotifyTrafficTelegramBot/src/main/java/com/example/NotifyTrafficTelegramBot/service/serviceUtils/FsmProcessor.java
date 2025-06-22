package com.example.NotifyTrafficTelegramBot.service.serviceUtils;

import com.example.NotifyTrafficTelegramBot.dto.UserInformationDto;
import com.example.NotifyTrafficTelegramBot.dto.UserSessionDto;
import com.example.NotifyTrafficTelegramBot.enums.States;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Getter
public class FsmProcessor {

    private final SessionStorage sessionStorage;

    public FsmProcessor(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public boolean handleFsmInput(Long chatId, String message, Consumer<String> replySender) {
        UserSessionDto session = sessionStorage.getSession(chatId);
        if (session == null || session.getState() == null) {
            replySender.accept("🪧 Пожалуйста, нажмите кнопку \"Начать настройку\".");
            return false;
        }

        switch (session.getState()) {
            case ASK_ARRIVAL_DATE -> {
                session.getInformationDto().setArrivalDate(message);
                session.setState(States.ASK_HOME_ADDRESS);
                replySender.accept("🏠 Введите домашний адрес:");
            }
            case ASK_HOME_ADDRESS -> {
                session.getInformationDto().setHomeAddress(message);
                session.setState(States.ASK_WORK_ADDRESS);
                replySender.accept("🏢 Введите адрес работы:");
            }
            case ASK_WORK_ADDRESS -> {
                session.getInformationDto().setWorkAddress(message);
                session.setState(States.ASK_TIMEZONE);
                replySender.accept("🌍 Укажите часовой пояс (например, UTC+3):");
            }
            case ASK_TIMEZONE -> {
                session.getInformationDto().setTimezone(message);
                session.setState(States.DONE);
                replySender.accept("✅ Все данные получены:\n\n" + format(session.getInformationDto()));
            }
            case DONE -> replySender.accept("👋 Настройка завершена. Напишите /start для новой.");
        }
        return true;
    }

    private String format(UserInformationDto data) {
        return "📅 Дата прибытия: " + data.getArrivalDate() + "\n" +
                "🏠 Домашний адрес: " + data.getHomeAddress() + "\n" +
                "🏢 Адрес работы: " + data.getWorkAddress() + "\n" +
                "🕒 Часовой пояс: " + data.getTimezone();
    }

}
