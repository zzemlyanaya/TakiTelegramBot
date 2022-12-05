package ru.zzemlyanaya.takibot.domain.utils;

/* created by zzemlyanaya on 12/11/2022 */

import ru.zzemlyanaya.takibot.core.utils.ResourceProvider;

import java.util.List;

public class Keyboards {

    static final List<String> START_BUTTONS = List.of(
        Command.CHECK.getBtnName(),
        Command.TODAY.getBtnName(),
        Command.ADD.getBtnName(),
        Command.STATISTIC.getBtnName()
    );

    static final List<String> HABIT_TYPE_BUTTONS = List.of(
        ResourceProvider.getString("HabitTypeBinary"),
        ResourceProvider.getString("HabitTypeMeasurable")
    );

    static final List<String> FREQUENCY_TYPE_BUTTONS = List.of(
        ResourceProvider.getString("FrequencyEveryday"),
        ResourceProvider.getString("FrequencyOnceNDay")
    );

    static final List<String> STATISTIC_INTERVAL_BUTTONS = List.of(
        ResourceProvider.getString("1week"),
        ResourceProvider.getString("1month"),
        ResourceProvider.getString("3month")
    );
}
