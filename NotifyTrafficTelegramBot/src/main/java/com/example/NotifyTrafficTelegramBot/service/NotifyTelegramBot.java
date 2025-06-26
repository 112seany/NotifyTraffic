package com.example.NotifyTrafficTelegramBot.service;

import com.example.NotifyTrafficTelegramBot.service.serviceUtils.CommandHandler;
import com.example.NotifyTrafficTelegramBot.service.serviceUtils.FsmProcessor;
import com.example.NotifyTrafficTelegramBot.service.serviceUtils.SessionStorage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
@Getter
@Setter
public class NotifyTelegramBot extends TelegramLongPollingBot {

    @Autowired
    private FsmProcessor fsmProcessor;

    @Autowired
    private CommandHandler commandHandler;

    @Autowired
    private SessionStorage sessionStorage;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public NotifyTelegramBot(FsmProcessor fsmProcessor,
                             CommandHandler commandHandler,
                             SessionStorage sessionStorage) {

        this.fsmProcessor = fsmProcessor;
        this.commandHandler = commandHandler;
        this.sessionStorage = sessionStorage;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallback(update);
            return;
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String msg = update.getMessage().getText();

            boolean handled = commandHandler.handleCommand(
                    msg,
                    () -> sendStartMessage(chatId),
                    () -> sessionStorage.endSession(chatId),
                    text -> sendMessage(chatId, text)
            );

            if (!handled) {
                fsmProcessor.handleFsmInput(chatId, msg, text -> sendMessage(chatId, text));
            }
        }
    }

    private void handleCallback(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String data = update.getCallbackQuery().getData();

        if ("start_setup".equals(data)) {
            sessionStorage.startSession(chatId);
            sendMessage(chatId, "📅 Введите время прибытия на работу (например, 09:00):");
        }
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendStartMessage(Long chatId) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(
                List.of(List.of(InlineKeyboardButton.builder()
                        .text("🔧 Начать настройку")
                        .callbackData("start_setup")
                        .build()))
        );

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("👋 Привет! Я помогу тебе планировать выезд.\n\n🔧 Нажми кнопку ниже, чтобы начать настройку.")
                .replyMarkup(markup)
                .build();

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

