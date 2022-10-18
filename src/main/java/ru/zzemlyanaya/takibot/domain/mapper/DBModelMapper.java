package ru.zzemlyanaya.takibot.domain.mapper;

/* created by zzemlyanaya on 08/11/2022 */

import ru.zzemlyanaya.takibot.data.model.Habit;
import ru.zzemlyanaya.takibot.data.model.User;
import ru.zzemlyanaya.takibot.domain.model.HabitEntity;
import ru.zzemlyanaya.takibot.domain.model.UserEntity;

import java.util.List;

public class DBModelMapper {

    public static UserEntity mapUserEntity(User model) {
        return new UserEntity(model.getPlatformId(), model.getUsername());
    }

    public static List<HabitEntity> mapHabits(List<Habit> habits) {
        return habits.stream().map(DBModelMapper::mapHabitEntity).toList();
    }

    public static HabitEntity mapHabitEntity(Habit habit) {
        return new HabitEntity(
            habit.getUserId(),
            habit.getName(),
            habit.getPrompt(),
            habit.getIsBinary(),
            habit.getFrequency(),
            habit.getMetric(),
            habit.getMetricGoal()
        );
    }

    public static Habit mapHabitModel(HabitEntity habit) {
        return new Habit(
            habit.getUserId(),
            habit.getName(),
            habit.getPrompt(),
            habit.getIsBinary(),
            habit.getFrequency(),
            habit.getMetric(),
            habit.getMetricGoal()
        );
    }
}
