package ru.zzemlyanaya.takibot.domain.handlers;

import io.reactivex.rxjava3.schedulers.Schedulers;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zzemlyanaya.takibot.core.handlers.CoreHandler;
import ru.zzemlyanaya.takibot.core.utils.ResourceProvider;
import ru.zzemlyanaya.takibot.data.service.TakiDbService;
import ru.zzemlyanaya.takibot.data.service.impl.TakiDbServiceImpl;
import ru.zzemlyanaya.takibot.domain.model.HabitEntity;
import ru.zzemlyanaya.takibot.domain.utils.Command;
import ru.zzemlyanaya.takibot.domain.utils.KeyboardFactory;

import java.time.LocalDate;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static ru.zzemlyanaya.takibot.core.utils.Predicates.*;

/* created by zzemlyanaya on 10/11/2022 */

public class AddHabitHandler implements CoreHandler {

    private static final Logger log = LogManager.getLogger();

    private final TakiDbService dbService = TakiDbServiceImpl.INSTANCE;

    private final SilentSender silent;
    private final DBContext db;

    public AddHabitHandler(SilentSender silent, DBContext dbContext) {
        this.silent = silent;
        this.db = dbContext;
    }

    @Override
    public boolean support(Command command) {
        return command == Command.ADD;
    }

    @Override
    public Ability startFlow() {
        return Ability.builder()
            .name(Command.ADD.getName())
            .locality(ALL)
            .privacy(PUBLIC)
            .action(ctx -> {
            })
            .reply(getReplyFlow())
            .build();
    }

    private ReplyFlow getReplyFlow() {
        HabitEntity habit = new HabitEntity();
        return ReplyFlow.builder(db)
            .onlyIf(hasCommand(Command.ADD))
            .action((bot, upd) -> {
                habit.setUserId(AbilityUtils.getUser(upd).getId());
                silent.send(ResourceProvider.getString("AddFlowFirstMessage"), AbilityUtils.getChatId(upd));
                askHabitType(upd);
            })
            .next(onBinaryHabitChosen(habit))
            .next(onMeasurableHabitChosen(habit))
            .build();
    }

    private void askHabitType(Update upd) {
        SendMessage message = new SendMessage();

        message.enableMarkdown(true);
        message.setReplyMarkup(KeyboardFactory.getHabitTypeKeyboard());
        message.setChatId(AbilityUtils.getChatId(upd));
        message.setText(ResourceProvider.getString("ChooseHabitType"));

        silent.execute(message);
    }

    private ReplyFlow onBinaryHabitChosen(HabitEntity habit) {
        return ReplyFlow
            .builder(db)
            .onlyIf(hasMessageWith(ResourceProvider.getString("HabitTypeBinary")))
            .action((bot, upd) -> {
                habit.setIsBinary(true);
                silent.forceReply(ResourceProvider.getString("EnterHabitName"), AbilityUtils.getChatId(upd));
            })
            .next(getName(habit, getPromptAndAskFrequency(habit)))
            .build();
    }

    private ReplyFlow onMeasurableHabitChosen(HabitEntity habit) {
        return ReplyFlow
            .builder(db)
            .onlyIf(hasMessageWith(ResourceProvider.getString("HabitTypeMeasurable")))
            .action((bot, upd) -> {
                habit.setIsBinary(false);
                silent.forceReply(ResourceProvider.getString("EnterHabitName"), AbilityUtils.getChatId(upd));
            })
            .next(getName(habit, getPromptAndAskMetric(habit)))
            .build();
    }

    private ReplyFlow getName(HabitEntity habit, ReplyFlow next) {
        return ReplyFlow
            .builder(db)
            .onlyIf(isReplyToMessage(ResourceProvider.getString("EnterHabitName")))
            .action((bot, upd) -> {
                habit.setName(upd.getMessage().getText());
                silent.forceReply(ResourceProvider.getString("EnterHabitPrompt"), AbilityUtils.getChatId(upd));
            })
            .next(next)
            .build();
    }

    private ReplyFlow getPromptAndAskFrequency(HabitEntity habit) {
        return ReplyFlow
            .builder(db)
            .onlyIf(isReplyToMessage(ResourceProvider.getString("EnterHabitPrompt")))
            .action((bot, upd) -> {
                habit.setPrompt(upd.getMessage().getText());
                askFrequency(upd);
            })
            .next(onFrequencyEverydayChosen(habit))
            .next(onFrequencyNChosen(habit))
            .build();
    }

    private ReplyFlow getPromptAndAskMetric(HabitEntity habit) {
        return ReplyFlow
            .builder(db)
            .onlyIf(isReplyToMessage(ResourceProvider.getString("EnterHabitPrompt")))
            .action((bot, upd) -> {
                habit.setPrompt(upd.getMessage().getText());
                silent.forceReply(ResourceProvider.getString("EnterMeasurements"), AbilityUtils.getChatId(upd));
            })
            .next(getMetric(habit))
            .build();
    }

    private ReplyFlow getMetric(HabitEntity habit) {
        return ReplyFlow
            .builder(db)
            .onlyIf(isReplyToMessage(ResourceProvider.getString("EnterMeasurements")))
            .action((bot, upd) -> {
                habit.setMetric(upd.getMessage().getText());
                silent.forceReply(ResourceProvider.getString("EnterGoal"), AbilityUtils.getChatId(upd));
            })
            .next(getGoal(habit))
            .build();
    }


    private ReplyFlow getGoal(HabitEntity habit) {
        return ReplyFlow
            .builder(db)
            .onlyIf(isReplyToMessage(ResourceProvider.getString("EnterGoal")))
            .action((bot, upd) -> {
                String goal = upd.getMessage().getText();
                if (NumberUtils.isCreatable(goal)) {
                    habit.setMetricGoal(Double.parseDouble(goal));
                    askFrequency(upd);
                } else {
                    sendErrorMessage(AbilityUtils.getChatId(upd));
                }
            })
            .next(onFrequencyEverydayChosen(habit))
            .next(onFrequencyNChosen(habit))
            .build();
    }

    private void askFrequency(Update upd) {
        SendMessage message = new SendMessage();

        message.enableMarkdown(true);
        message.setReplyMarkup(KeyboardFactory.getFrequencyTypeKeyboard());
        message.setChatId(AbilityUtils.getChatId(upd));
        message.setText(ResourceProvider.getString("EnterFrequency"));

        silent.execute(message);
    }

    private Reply onFrequencyEverydayChosen(HabitEntity habit) {
        return Reply.of((bot, upd) -> {
                habit.setFrequency(1);
                saveHabit(upd, habit);
            },
            hasMessageWith(ResourceProvider.getString("FrequencyEveryday"))
        );
    }

    private ReplyFlow onFrequencyNChosen(HabitEntity habit) {
        return ReplyFlow
            .builder(db)
            .onlyIf(hasMessageWith(ResourceProvider.getString("FrequencyOnceNDay")))
            .action((bot, upd) -> {
                silent.forceReply(ResourceProvider.getString("EnterN"), AbilityUtils.getChatId(upd));
            })
            .next(getFrequencyN(habit))
            .build();
    }

    private Reply getFrequencyN(HabitEntity habit) {
        return Reply.of(
            (bot, upd) -> {
                String n = upd.getMessage().getText();
                if (StringUtils.isNumeric(n)) {
                    habit.setFrequency(Integer.parseInt(n));
                    habit.setNextDate(LocalDate.now());
                    saveHabit(upd, habit);
                } else {
                    sendErrorMessage(AbilityUtils.getChatId(upd));
                }
            },
            isReplyToMessage(ResourceProvider.getString("EnterN"))
        );
    }

    private void saveHabit(Update upd, HabitEntity habit) {
        dbService.saveHabit(habit)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .doOnError(e -> {
                log.error(e);
                sendErrorMessage(AbilityUtils.getChatId(upd));
            })
            .doOnComplete(() -> sendSuccessMessage(AbilityUtils.getChatId(upd)))
            .subscribe();
    }

    private void sendSuccessMessage(Long chatId) {
        SendMessage message = getBaseMessage(chatId);
        message.setText(ResourceProvider.getString("AddFlowSuccess"));

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
