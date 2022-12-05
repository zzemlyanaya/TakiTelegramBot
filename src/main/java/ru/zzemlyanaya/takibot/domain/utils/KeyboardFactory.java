package ru.zzemlyanaya.takibot.domain.utils;

/* created by zzemlyanaya on 19/10/2022 */

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static ru.zzemlyanaya.takibot.domain.utils.Keyboards.*;

public class KeyboardFactory {

    public static ReplyKeyboard getMainKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        START_BUTTONS.forEach(btn -> {
            KeyboardRow row = new KeyboardRow();
            row.add(btn);
            keyboard.add(row);
        });
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    public static ReplyKeyboard getHabitTypeKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        HABIT_TYPE_BUTTONS.forEach(btn -> {
            KeyboardRow row = new KeyboardRow();
            row.add(btn);
            keyboard.add(row);
        });
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    public static ReplyKeyboard getFrequencyTypeKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        FREQUENCY_TYPE_BUTTONS.forEach(btn -> {
            KeyboardRow row = new KeyboardRow();
            row.add(btn);
            keyboard.add(row);
        });
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    public static ReplyKeyboard getNumericKeyboard(int max) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        int rowLength = (int) Math.ceil(max/3.0);
        for (int i = 1; i <= max; i++) {
            row.add(String.valueOf(i));
            if (i % rowLength == 0) {
                keyboard.add(row);
                row = new KeyboardRow();
            }
        }
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    public static ReplyKeyboard getStatisticIntervalKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        STATISTIC_INTERVAL_BUTTONS.forEach(btn -> {
            KeyboardRow row = new KeyboardRow();
            row.add(btn);
            keyboard.add(row);
        });
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }
}
