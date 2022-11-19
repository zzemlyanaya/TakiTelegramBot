package ru.zzemlyanaya.takibot.domain.handlers;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zzemlyanaya.takibot.core.handlers.CoreHandler;
import ru.zzemlyanaya.takibot.core.handlers.UpdateHandler;
import ru.zzemlyanaya.takibot.domain.utils.Command;

import java.util.stream.Stream;

/* created by zzemlyanaya on 19/10/2022 */

public class TelegramUpdateHandler implements UpdateHandler<Update> {

    private final StartHandler startHandler;
    private final MessageEventHandler messageHandler;
    private final AddHabitHandler addHabitHandler;
    private final CheckHabitHandler checkHabitHandler;

    public TelegramUpdateHandler(SilentSender silent, DBContext dbContext) {
        startHandler = new StartHandler(silent);
        messageHandler = new MessageEventHandler(silent);
        addHabitHandler = new AddHabitHandler(silent, dbContext);
        checkHabitHandler = new CheckHabitHandler(silent, dbContext);
    }

    @Override
    public CoreHandler getHandler(Command command) {
        return getHandlers()
            .filter(t -> t.support(command))
            .findFirst()
            .orElse(messageHandler);
    }

    @Override
    public Update normaliseRequest(Update request) {
        if (request.hasMessage()) {
            Message msg = request.getMessage();
            msg.setText(Command.asCommandIfPossible(msg.getText()));
            request.setMessage(msg);
        }
        return request;
    }

    private Stream<CoreHandler> getHandlers() {
        Stream.Builder<CoreHandler> builder = Stream.builder();

        return builder
            .add(startHandler)
            .add(addHabitHandler)
            .add(checkHabitHandler)
            .build();
    }
}
