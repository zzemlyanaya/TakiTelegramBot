package ru.zzemlyanaya.takibot.domain.bot;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.zzemlyanaya.takibot.core.handlers.UpdateHandler;
import ru.zzemlyanaya.takibot.core.utils.ConfigReader;
import ru.zzemlyanaya.takibot.core.utils.XmlResourceBundleControl;
import ru.zzemlyanaya.takibot.domain.handlers.TelegramUpdateHandler;
import ru.zzemlyanaya.takibot.domain.utils.Command;

import java.util.ResourceBundle;

/* created by zzemlyanaya on 05/10/2022 */

public class TakiBot extends AbilityBot {
    private static final ConfigReader CONFIG = ConfigReader.getInstance();
    private static final ResourceBundle res =
        ResourceBundle.getBundle("strings", new XmlResourceBundleControl());

    private static final String BOT_TOKEN = CONFIG.get("telegram.bot.token");
    private static final String BOT_USERNAME = CONFIG.get("telegram.bot.username");
    private static final Long DEVELOPER_ID = Long.valueOf(CONFIG.get("telegram.developer.id"));

    private final UpdateHandler<Update> handler = new TelegramUpdateHandler(silent(), db());

    public TakiBot() throws TelegramApiException {
        super(BOT_TOKEN, BOT_USERNAME);

        SetMyCommands commands = new SetMyCommands();
        commands.setCommands(Command.getBotCommands());
        this.execute(commands);
    }

    @Override
    public long creatorId() {
        return DEVELOPER_ID;
    }

    @Override
    public void onUpdateReceived(Update update) {
        super.onUpdateReceived(handler.normaliseRequest(update));
    }

    public Ability start() {
        return handler.getHandler(Command.START).startFlow();
    }

    public Ability onMessageReceived() {
        return handler.getHandler(Command.MESSAGE).startFlow();
    }

    public Ability addNewHabit() {
        return handler.getHandler(Command.ADD).startFlow();
    }

    public Ability checkHabit() {
        return handler.getHandler(Command.CHECK).startFlow();
    }

    public Ability showStatisticToday() {
        return handler.getHandler(Command.TODAY).startFlow();
    }

    public Ability showStatistic() {
        return handler.getHandler(Command.STATISTIC).startFlow();
    }

}