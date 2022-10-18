package ru.zzemlyanaya.takibot.domain.handlers;

import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.sender.SilentSender;
import ru.zzemlyanaya.takibot.core.handlers.CoreHandler;
import ru.zzemlyanaya.takibot.domain.utils.Command;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

/* created by zzemlyanaya on 04/10/2022 */

public class MessageEventHandler implements CoreHandler {

    private final SilentSender silent;

    public MessageEventHandler(SilentSender silent) {
        this.silent = silent;
    }

    @Override
    public boolean support(Command command) {
        return true;
    }

    @Override
    public Ability startFlow() {
        return Ability
            .builder()
            .name(Command.MESSAGE.getName())
            .input(1)
            .locality(ALL)
            .privacy(PUBLIC)
            .action(context -> silent.send(context.firstArg(), context.chatId()))
            .build();
    }
}
