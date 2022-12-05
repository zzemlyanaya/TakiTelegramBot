package ru.zzemlyanaya.takibot.domain.mapper;

/* created by zzemlyanaya on 03/12/2022 */

import ru.zzemlyanaya.takibot.domain.model.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticUiModelMapper {

    private static final int RED_EXCLAMATION_MARK = 0x2757;
    private static final int CHECK_MARK = 0x2705;
    private static final int UP_EMOJI = 0x1F199;
    private static final int PROGRESS_BLOCK = 0x25FB;

    public static TodayUiModel getTodayModel(List<HabitEntity> habits, List<EntryEntity> entries) {
        List<HabitUiModel> finished = new ArrayList<>();
        List<HabitUiModel> inProgress = new ArrayList<>();
        List<HabitUiModel> toDo = new ArrayList<>();

        habits.forEach(habit -> {
            EntryEntity entry = entries.stream().filter(it -> it.getHabitId() == habit.getId()).findFirst().orElse(null);

            if (entry == null) {
                toDo.add(mapTodoHabit(habit));
            } else {
                if (habit.getIsBinary() || entry.getAchieved() >= habit.getMetricGoal()) {
                    finished.add(mapToFinishedHabit(habit));
                } else {
                    inProgress.add(mapToInProgressHabit(habit, entry.getAchieved()));
                }
            }
        });

        int dayProgress = (int)(entries.size()/(double)habits.size()*100);
        return new TodayUiModel(finished, inProgress, toDo, getProgressLine(dayProgress));
    }

    private static HabitUiModel mapTodoHabit(HabitEntity habit) {
        return new HabitUiModel(
            habit.getId(),
            new String(Character.toChars(RED_EXCLAMATION_MARK)) + habit.getName(),
            "");
    }

    private static HabitUiModel mapToFinishedHabit(HabitEntity habit) {
        return new HabitUiModel(
            habit.getId(),
            new String(Character.toChars(CHECK_MARK)) + habit.getName(),
            "");
    }

    private static HabitUiModel mapToInProgressHabit(HabitEntity habit, double progress) {
        int percents = (int)(progress/habit.getMetricGoal()*100);
        String progressLine = getProgressLine(percents);

        return new HabitUiModel(
            habit.getId(),
            new String(Character.toChars(UP_EMOJI)) + habit.getName(),
            progressLine
        );
    }

    private static String getProgressLine(int percent) {
        return new String(Character.toChars(PROGRESS_BLOCK)).repeat(percent/14) + "%d%%\n".formatted(percent);
    }

    public static List<ChartEntryModel> getChartModel(List<EntryEntity> entries, List<HabitEntity> habits) {
        int allHabits = habits.size();
        List<ChartEntryModel> result = new ArrayList<>();

        entries.stream()
            .collect(Collectors.groupingBy(EntryEntity::getDate, Collectors.counting()))
            .forEach( (date, count) ->
                result.add(new ChartEntryModel(
                    (int)(count/(double)allHabits*100),
                    date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                )
            );

        return result;
    }
}
