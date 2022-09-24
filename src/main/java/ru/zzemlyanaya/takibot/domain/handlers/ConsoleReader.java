package ru.zzemlyanaya.takibot.domain.handlers;

import ru.zzemlyanaya.takibot.core.handlers.ReadHandler;

import java.util.Scanner;

/* created by zzemlyanaya on 24/09/2022 */

public class ConsoleReader implements ReadHandler {
    private final Scanner in = new Scanner(System.in);

    @Override
    public String handle() {
        return in.nextLine();
    }
}
