package ru.zzemlyanaya.takibot.domain.handlers;

/* created by zzemlyanaya on 03/12/2022 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.abilitybots.api.util.Pair;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.zzemlyanaya.takibot.core.handlers.CoreHandler;
import ru.zzemlyanaya.takibot.core.utils.ResourceProvider;
import ru.zzemlyanaya.takibot.data.service.TakiDbService;
import ru.zzemlyanaya.takibot.data.service.impl.TakiDbServiceImpl;
import ru.zzemlyanaya.takibot.domain.mapper.StatisticUiModelMapper;
import ru.zzemlyanaya.takibot.domain.model.ChartEntryModel;
import ru.zzemlyanaya.takibot.domain.utils.ChartGenerator;
import ru.zzemlyanaya.takibot.domain.utils.Command;
import ru.zzemlyanaya.takibot.domain.utils.KeyboardFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static ru.zzemlyanaya.takibot.core.utils.Predicates.hasCommand;
import static ru.zzemlyanaya.takibot.core.utils.Predicates.hasMessageWith;

public class StatisticHandler implements CoreHandler {

    private static final Logger log = LogManager.getLogger();

    private final TakiDbService dbService = TakiDbServiceImpl.INSTANCE;

    private final SilentSender silent;
    private MessageSender sender;
    private final DBContext db;

    public StatisticHandler(SilentSender silent, DBContext db) {
        this.silent = silent;
        this.db = db;
    }

    @Override
    public boolean support(Command command) {
        return command == Command.STATISTIC;
    }

    @Override
    public Ability startFlow() {
        return Ability.builder()
            .name(Command.STATISTIC.getName())
            .locality(ALL)
            .privacy(PUBLIC)
            .action(ctx -> {})
            .reply(getReplyFlow())
            .build();
    }

    private ReplyFlow getReplyFlow() {
        return ReplyFlow.builder(db)
            .onlyIf(hasCommand(Command.STATISTIC))
            .action((bot, upd) -> askInterval(upd))
            .next(on1WeekChosen())
            .next(on1MonthChosen())
            .next(on3MonthsChosen())
            .build();
    }

    private void askInterval(Update upd) {
        SendMessage message = new SendMessage();

        message.enableMarkdown(true);
        message.setReplyMarkup(KeyboardFactory.getStatisticIntervalKeyboard());
        message.setChatId(AbilityUtils.getChatId(upd));
        message.setText(ResourceProvider.getString("StatisticChooseInterval"));

        silent.execute(message);
    }

    private ReplyFlow on1WeekChosen() {
        return ReplyFlow
            .builder(db)
            .onlyIf(hasMessageWith(ResourceProvider.getString("1week")))
            .action((bot, upd) -> {
                sender = bot.sender();
                getStatistic(upd, LocalDate.now().minusWeeks(1), LocalDate.now());
            })
            .build();
    }

    private ReplyFlow on1MonthChosen() {
        return ReplyFlow
            .builder(db)
            .onlyIf(hasMessageWith(ResourceProvider.getString("1month")))
            .action((bot, upd) -> getStatistic(upd, LocalDate.now().minusMonths(1), LocalDate.now()))
            .build();
    }

    private ReplyFlow on3MonthsChosen() {
        return ReplyFlow
            .builder(db)
            .onlyIf(hasMessageWith(ResourceProvider.getString("3month")))
            .action((bot, upd) -> getStatistic(upd, LocalDate.now().minusMonths(3), LocalDate.now()))
            .build();
    }

    private void getStatistic(Update upd, LocalDate start, LocalDate end) {
        dbService.getEntriesBetween(AbilityUtils.getUser(upd).getId(), start, end)
            .zipWith(dbService.getHabitsByUserId(AbilityUtils.getUser(upd).getId()), Pair::of)
            .map(result -> StatisticUiModelMapper.getChartModel(result.a(), result.b()))
            .doOnError(e -> {
                log.error(e);
                sendErrorMessage(AbilityUtils.getChatId(upd));
            })
            .doOnSuccess(data -> sendStatistic(data, upd))
            .subscribe();
    }

    private void sendStatistic(List<ChartEntryModel> chartEntryModels, Update upd) {
        Long chatId = AbilityUtils.getChatId(upd);
        try {
            Long userId = AbilityUtils.getUser(upd).getId();
            ChartGenerator.generate(chartEntryModels, userId);
            File stats = new File("generated/%s.png".formatted(userId));

            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatId);
            sendPhoto.setPhoto(new InputFile(stats));

            sender.sendPhoto(sendPhoto);
            stats.delete();
            sendSuccessMessage(chatId);
        } catch (IOException | TelegramApiException e) {
            log.error(e);
            sendErrorMessage(chatId);
        }
    }

    private void sendSuccessMessage(Long chatId) {
        SendMessage message = getBaseMessage(chatId);
        message.setText(ResourceProvider.getString("StatisticFinal"));

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
