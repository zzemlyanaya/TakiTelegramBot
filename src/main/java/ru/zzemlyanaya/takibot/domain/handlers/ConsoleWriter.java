package ru.zzemlyanaya.takibot.domain.handlers;

import ru.zzemlyanaya.takibot.core.handlers.WriteHandler;
import ru.zzemlyanaya.takibot.core.model.EventData;
import ru.zzemlyanaya.takibot.domain.model.MessageData;

/* created by zzemlyanaya on 24/09/2022 */

public class ConsoleWriter implements WriteHandler {
    @Override
    public void handle(EventData<MessageData> data) {
        System.out.printf(data.getData().getMessage());
    }
}
