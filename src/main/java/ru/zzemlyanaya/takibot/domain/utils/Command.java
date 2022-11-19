package ru.zzemlyanaya.takibot.domain.utils;

/* created by zzemlyanaya on 10/10/2022 */

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public enum Command {
    START("start", "start"),
    MESSAGE("unknown", ""),
    COUNT("count", ""),
    ADD("add", "Новая привычка"),
    CHECK("check", "Отметить прогресс"),
    TODAY("today", "Сегодня"),
    STATISTIC("statistics", "Статистика");

    private final String name;

    private final String btnName;

    Command(String name, String btnName) {
        this.name = name;
        this.btnName = btnName;
    }

    public String getName() {
        return name;
    }

    public String getBtnName() {
        return btnName;
    }

    public static String asCommandIfPossible(String msg) {
        Command received = Arrays.stream(values()).filter(matchName(msg)).findFirst().orElse(null);

        if (received != null) {
            return getFullCommand(received);
        } else {
            return msg;
        }
    }

    public static List<BotCommand> getBotCommands() {
       return getUserCommands().map(
           command -> new BotCommand(command.getName(), command.getBtnName())
       ).toList();
    }

    private static Predicate<Command> matchName(String msg) {
        return command -> getFullCommand(command).equalsIgnoreCase(msg)
            || command.btnName.equalsIgnoreCase(msg);
    }

    private static String getFullCommand(Command command) {
        return "/" + command.name;
    }

    private static Stream<Command> getUserCommands() {
        return Stream.of(ADD, CHECK, TODAY, STATISTIC);
    }
}
