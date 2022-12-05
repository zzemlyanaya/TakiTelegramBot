package ru.zzemlyanaya.takibot.domain.handlers;

/* created by zzemlyanaya on 03/12/2022 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.abilitybots.api.util.Pair;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zzemlyanaya.takibot.core.handlers.CoreHandler;
import ru.zzemlyanaya.takibot.core.utils.ResourceProvider;
import ru.zzemlyanaya.takibot.data.service.TakiDbService;
import ru.zzemlyanaya.takibot.data.service.impl.TakiDbServiceImpl;
import ru.zzemlyanaya.takibot.domain.mapper.StatisticUiModelMapper;
import ru.zzemlyanaya.takibot.domain.model.TodayUiModel;
import ru.zzemlyanaya.takibot.domain.utils.Command;
import ru.zzemlyanaya.takibot.domain.utils.KeyboardFactory;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static ru.zzemlyanaya.takibot.core.utils.Predicates.hasCommand;

public class TodayHandler implements CoreHandler  {

    private static final Logger log = LogManager.getLogger();

    private final TakiDbService dbService = TakiDbServiceImpl.INSTANCE;

    private final SilentSender silent;
    private final DBContext db;

    public TodayHandler(SilentSender silent, DBContext db) {
        this.silent = silent;
        this.db = db;
    }

    @Override
    public boolean support(Command command) {
        return command == Command.TODAY;
    }

    @Override
    public Ability startFlow() {
        return Ability.builder()
            .name(Command.TODAY.getName())
            .locality(ALL)
            .privacy(PUBLIC)
            .action(ctx -> {})
            .reply(getReplyFlow())
            .build();
    }

    private ReplyFlow getReplyFlow() {
        return ReplyFlow.builder(db)
            .onlyIf(hasCommand(Command.TODAY))
            .action((bot, upd) -> {
                silent.send(ResourceProvider.getString("Loading"), AbilityUtils.getChatId(upd));
                getHabits(upd);
            })
            .build();
    }

    private void getHabits(Update upd) {
        dbService.getHabitsByUserId(AbilityUtils.getUser(upd).getId())
            .zipWith(dbService.getTodayEntries(AbilityUtils.getUser(upd).getId()), Pair::of)
            .map(result -> StatisticUiModelMapper.getTodayModel(result.a(), result.b()))
            .doOnError(e -> {
                log.error(e);
                sendErrorMessage(AbilityUtils.getChatId(upd));
            })
            .doOnSuccess(today -> sendTodayMessage(today, AbilityUtils.getChatId(upd)))
            .subscribe();
    }

    private void sendTodayMessage(TodayUiModel todayUiModel, Long chatId) {
        SendMessage message = getBaseMessage(chatId);
        message.enableMarkdown(true);
        StringBuilder text = new StringBuilder();

        if (!todayUiModel.finished().isEmpty()) {
            text.append("\n");
            text.append("*").append(ResourceProvider.getString("TodayFinished")).append("*");
            todayUiModel.finished().forEach(habit -> text.append(habit.getDisplayName()).append("\n"));
        }
        if (!todayUiModel.inProgress().isEmpty()) {
            text.append("\n");
            text.append("*").append(ResourceProvider.getString("TodayInProgress")).append("*");
            todayUiModel.inProgress().forEach(habit -> text.append(habit.getDisplayName()).append("\n").append(habit.getProgressLine()));
        }
        if (!todayUiModel.toDo().isEmpty()) {
            text.append("\n");
            text.append("*").append(ResourceProvider.getString("TodayToDo")).append("*");
            todayUiModel.toDo().forEach(habit -> text.append(habit.getDisplayName()).append("\n"));
        }

        if (text.isEmpty()) {
            message.setText(ResourceProvider.getString("TodayProgressEmpty"));
        } else {
            text.insert(0, todayUiModel.dayProgressLine()).append("\n");
            text.insert(0, ResourceProvider.getString("TodayProgress"));
            message.setText(text.toString());
        }
        silent.execute(message);
    }

    private void sendErrorMessage(Long chatId) {
        SendMessage message = getBaseMessage(chatId);
        message.setText(ResourceProvider.getString("DefaultErrorMessage"));

        silent.execute(message);
    }

    private SendMessage getBaseMessage(Long chatId) {
        return SendMessage
            .builder()
            .chatId(chatId)
            .text("")
            .replyMarkup(KeyboardFactory.getMainKeyboard())
            .build();
    }
}
