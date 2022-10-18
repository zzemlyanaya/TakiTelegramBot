package ru.zzemlyanaya.takibot.core.handlers;

/* created by zzemlyanaya on 19/10/2022 */

import ru.zzemlyanaya.takibot.domain.utils.Command;

public interface UpdateHandler<T> {

    CoreHandler getHandler(Command command);

    T normaliseRequest(T request);
}
