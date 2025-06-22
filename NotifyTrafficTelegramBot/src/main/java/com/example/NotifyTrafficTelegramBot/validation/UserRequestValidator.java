package com.example.NotifyTrafficTelegramBot.validation;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class UserRequestValidator {

    public static boolean isValidArrivalTime(String input) {
        try {
            LocalTime.parse(input, DateTimeFormatter.ofPattern("HH:mm"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidTimezone(String input) {
        return !Objects.isNull(input) && input.matches("UTC[+-]\\d{1,2}");
    }
}
