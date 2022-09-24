package ru.zzemlyanaya.takibot.domain.handlers;

/* created by zzemlyanaya on 24/09/2022 */

import ru.zzemlyanaya.takibot.core.handlers.LoopHandler;
import ru.zzemlyanaya.takibot.core.handlers.MessageHandler;
import ru.zzemlyanaya.takibot.core.handlers.ReadHandler;
import ru.zzemlyanaya.takibot.core.handlers.WriteHandler;

public class Looper implements LoopHandler {
    private final ReadHandler reader;
    private final WriteHandler writer;

    private final MessageHandler messageHandler;
    private final String stopSequence;

    public Looper(
        ReadHandler reader,
        WriteHandler writer,
        MessageHandler messageHandler,
        String stopSequence
    ) {
        this.reader = reader;
        this.writer = writer;
        this.messageHandler = messageHandler;
        this.stopSequence = stopSequence;
    }

    @Override
    public void startLoop() {
        String data = reader.handle();
        while (isContinue(data)) {
            writer.handle(messageHandler.handle(data));
            data = reader.handle();
        }
    }

    private boolean isContinue(String message) {
        return !stopSequence.equals(message);
    }
}
