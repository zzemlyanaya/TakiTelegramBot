package ru.zzemlyanaya.takibot.domain.model;

import java.util.List;

/* created by zzemlyanaya on 04/12/2022 */

public record TodayUiModel(
    List<HabitUiModel> finished,
    List<HabitUiModel> inProgress,
    List<HabitUiModel> toDo,
    String dayProgressLine
) { }
