package ru.zzemlyanaya.takibot;

/* created by zzemlyanaya on 24/09/2022 */

import io.reactivex.rxjava3.schedulers.Schedulers;
import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.zzemlyanaya.takibot.data.service.TakiDbService;
import ru.zzemlyanaya.takibot.data.service.impl.TakiDbServiceImpl;
import ru.zzemlyanaya.takibot.domain.bot.TakiBot;

public class Application {
    private static final String LOG_TAG = "-----TAKI-LOG-MAIN";
    private static final Logger log = LogManager.getLogger();

    private static final TakiDbService dbService = TakiDbServiceImpl.INSTANCE;

    public static void main(String[] args) {
        BasicConfigurator.configure();

        try {
            dbService.initDb().observeOn(Schedulers.io()).subscribe();
            new TelegramBotsApi(DefaultBotSession.class).registerBot(new TakiBot());
        } catch (TelegramApiException e) {
            log.error(LOG_TAG, e);
        }
    }
}