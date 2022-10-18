package ru.zzemlyanaya.takibot.core.handlers;

/* created by zzemlyanaya on 19/10/2022 */

import org.telegram.abilitybots.api.objects.Ability;
import ru.zzemlyanaya.takibot.domain.utils.Command;

public interface CoreHandler {

    boolean support(Command command);

    Ability startFlow();
}
