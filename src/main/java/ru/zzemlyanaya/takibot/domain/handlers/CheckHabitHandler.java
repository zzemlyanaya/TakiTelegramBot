package ru.zzemlyanaya.takibot.domain.handlers;

import io.reactivex.rxjava3.schedulers.Schedulers;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.zzemlyanaya.takibot.core.handlers.CoreHandler;
import ru.zzemlyanaya.takibot.core.utils.ResourceProvider;
import ru.zzemlyanaya.takibot.data.service.TakiDbService;
import ru.zzemlyanaya.takibot.data.service.impl.TakiDbServiceImpl;
import ru.zzemlyanaya.takibot.domain.model.CheckModel;
import ru.zzemlyanaya.takibot.domain.model.HabitEntity;
import ru.zzemlyanaya.takibot.domain.utils.Command;
import ru.zzemlyanaya.takibot.domain.utils.KeyboardFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static ru.zzemlyanaya.takibot.core.utils.Predicates.*;

/* created by zzemlyanaya on 10/11/2022 */

public class CheckHabitHandler implements CoreHandler {

    private static final Logger log = LogManager.getLogger();

    private final TakiDbService dbService = TakiDbServiceImpl.INSTANCE;

    private final SilentSender silent;
    private final DBContext db;

    public CheckHabitHandler(SilentSender silent, DBContext dbContext) {
        this.silent = silent;
        this.db = dbContext;
    }

    @Override
    public boolean support(Command command) {
        return command == Command.CHECK;
    }

    @Override
    public Ability startFlow() {
        return Ability.builder()
            .name(Command.CHECK.getName())
            .locality(ALL)
            .privacy(PUBLIC)
            .action(ctx -> {})
            .reply(getReplyFlow())
            .build();
    }

    private ReplyFlow getReplyFlow() {
        List<HabitEntity> habits = new ArrayList<>();

        return ReplyFlow.builder(db)
            .onlyIf(hasCommand(Command.CHECK))
            .action((bot, upd) -> {
                silent.send(ResourceProvider.getString("CheckFlowFirstMessage"), AbilityUtils.getChatId(upd));
                getHabits(habits, upd);
            })
            .next(onHabitChosen(habits))
            .build();
    }

    private void getHabits(List<HabitEntity> habits, Update upd) {
        dbService.getHabitsByUserAndDate(AbilityUtils.getUser(upd).getId(), LocalDate.now())
            .doOnError(e -> {
                log.error(e);
                sendErrorMessage(AbilityUtils.getChatId(upd));
            })
            .doOnSuccess(result -> {
                habits.clear();
                habits.addAll(result);
                askHabit(habits, upd);
            })
            .subscribe();
    }

    private void askHabit(List<HabitEntity> habits, Update upd) {
        SendMessage message = new SendMessage();

        message.enableMarkdown(true);
        message.setReplyMarkup(KeyboardFactory.getNumericKeyboard(habits.size()));
        message.setChatId(AbilityUtils.getChatId(upd));
        log.info(getHabitsMessage(habits));
        message.setText(getHabitsMessage(habits));

        silent.execute(message);
    }

    private ReplyFlow onHabitChosen(List<HabitEntity> habits) {
        CheckModel checkModel = new CheckModel();
        return ReplyFlow
            .builder(db)
            .onlyIf(hasNumericMessage())
            .action((bot, upd) -> {
                HabitEntity habit = habits.get(Integer.parseInt(upd.getMessage().getText())-1);
                checkModel.setHabit(habit);

                if (!habit.getIsBinary()) {
                    String prompt = ResourceProvider.getString("EnterAnswerIn").concat(habit.getMetric());

                    silent.send(habit.getPrompt(), AbilityUtils.getChatId(upd));
                    silent.forceReply(prompt, AbilityUtils.getChatId(upd));
                } else {
                    saveEntry(upd, checkModel);
                }
            })
            .next(getPromptAnswer(checkModel))
            .build();
    }

    private ReplyFlow getPromptAnswer(CheckModel checkModel) {
        return ReplyFlow
            .builder(db)
            .onlyIf(isReplyToMessageWith(ResourceProvider.getString("EnterAnswerIn")))
            .action((bot, upd) -> {
                String achieved = upd.getMessage().getText();
                if (NumberUtils.isCreatable(achieved)) {
                    checkModel.setAchieved(Double.parseDouble(achieved));
                    saveEntry(upd, checkModel);
                } else {
                    sendErrorMessage(AbilityUtils.getChatId(upd));
                }

            })
            .build();
    }

    private void saveEntry(Update upd, CheckModel checkModel) {
        dbService.runCheckTransaction(checkModel)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .doOnError(e -> {
                log.error(e);
                sendErrorMessage(AbilityUtils.getChatId(upd));
            })
            .doOnComplete(() -> sendSuccessMessage(AbilityUtils.getChatId(upd)))
            .subscribe();
    }

    private String getHabitsMessage(List<HabitEntity> habits) {
        StringBuilder res = new StringBuilder(ResourceProvider.getString("ChooseHabit"));
        for (int i = 0; i < habits.size(); i++) {
            res.append(String.format("%d. %s\n", i+1, habits.get(i).getName()));
        }

        return res.toString();
    }

    private void sendSuccessMessage(Long chatId) {
        SendMessage message = getBaseMessage(chatId);

        message.setText(ResourceProvider.getString("CheckFlowSuccessMessage"));
        silent.execute(message);

        message.setText(ResourceProvider.getString("CheckFlowProgressMessage"));
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
