package ru.zzemlyanaya.takibot.domain.handlers;

import ru.zzemlyanaya.takibot.core.handlers.MessageHandler;
import ru.zzemlyanaya.takibot.core.model.EventData;
import ru.zzemlyanaya.takibot.domain.model.MessageData;

/* created by zzemlyanaya on 04/10/2022 */
public class MessageEventHandler implements MessageHandler {
    @Override
    public EventData<MessageData> handle(String message) {
        return new EventData<>(new MessageData("Received: " + message + "\n"));
    }
}
