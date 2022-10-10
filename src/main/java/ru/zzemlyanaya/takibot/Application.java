package ru.zzemlyanaya.takibot;

/* created by zzemlyanaya on 24/09/2022 */

import org.apache.log4j.BasicConfigurator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.zzemlyanaya.takibot.domain.bot.TakiBot;

public class Application {
    private static final String LOG_TAG = "-----TAKI-LOG-MAIN";
    private static final Logger LOGGER = LogManager.getLogger();
    
    public static void main(String[] args) {
        BasicConfigurator.configure();

        try {
            new TelegramBotsApi(DefaultBotSession.class).registerBot(new TakiBot());
        } catch (TelegramApiException e) {
            LOGGER.error(LOG_TAG, e);
        }
    }
}