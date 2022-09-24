package ru.zzemlyanaya.takibot;

/* created by zzemlyanaya on 24/09/2022 */

import ru.zzemlyanaya.takibot.domain.handlers.ConsoleReader;
import ru.zzemlyanaya.takibot.domain.handlers.ConsoleWriter;
import ru.zzemlyanaya.takibot.domain.handlers.Looper;
import ru.zzemlyanaya.takibot.domain.handlers.MessageEventHandler;

public class Application {
    public static void main(String[] args) {
        ConsoleReader consoleReader = new ConsoleReader();
        ConsoleWriter consoleWriter = new ConsoleWriter();
        MessageEventHandler messageHandler = new MessageEventHandler();
        Looper looper = new Looper(consoleReader, consoleWriter, messageHandler,"stop");

        looper.startLoop();
    }
}