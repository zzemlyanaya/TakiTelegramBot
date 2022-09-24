package ru.zzemlyanaya.takibot.core.handlers;

/* created by zzemlyanaya on 24/09/2022 */

import ru.zzemlyanaya.takibot.core.model.EventData;
import ru.zzemlyanaya.takibot.domain.model.MessageData;

public interface WriteHandler {
    void handle(EventData<MessageData> data);
}
