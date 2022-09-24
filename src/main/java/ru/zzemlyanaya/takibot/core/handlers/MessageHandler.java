package ru.zzemlyanaya.takibot.core.handlers;/* created by zzemlyanaya on 04/10/2022 */

import ru.zzemlyanaya.takibot.core.model.EventData;
import ru.zzemlyanaya.takibot.domain.model.MessageData;

public interface MessageHandler {
    EventData<MessageData> handle(String message);
}
