package com.example.NotifyTrafficTelegramBot.service.serviceUtils;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class CommandHandler {

    public boolean handleCommand(String msg, Runnable startCallback, Runnable cancelCallback, Consumer<String> replySender) {
        return switch (msg) {
            case "/start" -> {
                startCallback.run();
                yield true;
            }
            case "/cancel" -> {
                cancelCallback.run();
                replySender.accept("❌ Настройка отменена.");
                yield true;
            }
            default -> false;
        };
    }
}
