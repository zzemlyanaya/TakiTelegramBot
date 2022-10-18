package ru.zzemlyanaya.takibot.domain.handlers;

import io.reactivex.rxjava3.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.zzemlyanaya.takibot.core.handlers.CoreHandler;
import ru.zzemlyanaya.takibot.core.utils.ResourceProvider;
import ru.zzemlyanaya.takibot.data.service.TakiDbService;
import ru.zzemlyanaya.takibot.data.service.impl.TakiDbServiceImpl;
import ru.zzemlyanaya.takibot.domain.utils.Command;
import ru.zzemlyanaya.takibot.domain.utils.KeyboardFactory;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

/* created by zzemlyanaya on 12/10/2022 */

public class StartHandler implements CoreHandler {

    private static final Logger log = LogManager.getLogger();

    private final TakiDbService dbService = TakiDbServiceImpl.INSTANCE;

    private final SilentSender silent;

    public StartHandler(SilentSender silent) {
        this.silent = silent;
    }

    @Override
    public boolean support(Command command) {
        return command == Command.START;
    }

    @Override
    public Ability startFlow() {
        return Ability
            .builder()
            .name(Command.START.getName())
            .locality(ALL)
            .privacy(PUBLIC)
            .action(this::initUser)
            .build();
    }

    private void initUser(MessageContext context) {
        dbService.initDb()
            .andThen(dbService.saveUser(context.user().getId(), context.user().getUserName()))
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .doOnError(e -> {
                log.error(e);
                sendErrorMessage(context);
            })
            .doOnComplete(() -> {
                silent.execute(createFirstMessage(context));
                silent.execute(createSecondMessage(context));
            })
            .subscribe();
    }

    private SendMessage createFirstMessage(MessageContext context) {
        SendMessage message = new SendMessage();

        message.setChatId(context.chatId());
        message.setText(ResourceProvider.getString("StartMessageFirst"));

        return message;
    }

    private SendMessage createSecondMessage(MessageContext context) {
        SendMessage message = new SendMessage();

        message.enableMarkdown(true);
        message.setReplyMarkup(KeyboardFactory.getMainKeyboard());
        message.setChatId(context.chatId());
        message.setText(ResourceProvider.getString("StartMessageSecond"));

        return message;
    }

    private void sendErrorMessage(MessageContext context) {
        silent.send(ResourceProvider.getString("DefaultErrorMessage"), context.chatId());
    }

}