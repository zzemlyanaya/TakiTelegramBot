package ru.zzemlyanaya.takibot.domain.bot;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import ru.zzemlyanaya.takibot.core.utils.ConfigReader;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

/* created by zzemlyanaya on 05/10/2022 */

public class TakiBot extends AbilityBot {
    private static final ConfigReader CONFIG = ConfigReader.getInstance();
    private static final String BOT_TOKEN = CONFIG.get("telegram.bot.token");
    private static final String BOT_USERNAME = CONFIG.get("telegram.bot.username");
    private static final Long DEVELOPER_ID = Long.valueOf(CONFIG.get("telegram.developer.id"));

    public TakiBot() {
        super(BOT_TOKEN, BOT_USERNAME);
    }

    @Override
    public long creatorId() {
        return DEVELOPER_ID;
    }

    public Ability start() {
        return Ability
                .builder()
                .name(Commands.START.getName())
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("This is START ability!", ctx.chatId()))
                .build();
    }

    public Ability stop() {
        return Ability
                .builder()
                .name(Commands.STOP.getName())
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("This is STOP ability!", ctx.chatId()))
                .build();
    }

    public Ability addNewHabit() {
        return Ability
                .builder()
                .name(Commands.ADD.getName())
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("This is ADD ability!", ctx.chatId()))
                .build();
    }

    public Ability checkHabit() {
        return Ability
                .builder()
                .name(Commands.CHECK.getName())
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("This is CHECK ability!", ctx.chatId()))
                .build();
    }

    public Ability showStatisticToday() {
        return Ability
                .builder()
                .name(Commands.TODAY.getName())
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("This is TODAY ability!", ctx.chatId()))
                .build();
    }

    public Ability showStatistic() {
        return Ability
                .builder()
                .name(Commands.STATISTIC.getName())
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("This is STATISTIC ability!", ctx.chatId()))
                .build();
    }

}