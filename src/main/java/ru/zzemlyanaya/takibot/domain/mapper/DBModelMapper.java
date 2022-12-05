package ru.zzemlyanaya.takibot.domain.mapper;

/* created by zzemlyanaya on 08/11/2022 */

import ru.zzemlyanaya.takibot.data.model.Entry;
import ru.zzemlyanaya.takibot.data.model.Habit;
import ru.zzemlyanaya.takibot.data.model.User;
import ru.zzemlyanaya.takibot.domain.model.EntryEntity;
import ru.zzemlyanaya.takibot.domain.model.HabitEntity;
import ru.zzemlyanaya.takibot.domain.model.UserEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            habit.getId(),
            habit.getUserId(),
            habit.getName(),
            habit.getPrompt(),
            habit.getIsBinary(),
            habit.getFrequency(),
            habit.getMetric(),
            habit.getMetricGoal(),
            LocalDate.parse(habit.getNextDate(), DateTimeFormatter.ISO_DATE)
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
            habit.getMetricGoal(),
            habit.getNextDate().format(DateTimeFormatter.ISO_DATE)
        );
    }

    public static Entry mapEntryModel(EntryEntity entry) {
        return new Entry(
            entry.getDate().format(DateTimeFormatter.ISO_DATE),
            entry.getUserId(),
            entry.getHabitId(),
            entry.getAchieved()
        );
    }

    public static EntryEntity mapEntryEntity(Entry entry) {
        return new EntryEntity(
            LocalDate.parse(entry.getDate(), DateTimeFormatter.ISO_DATE),
            entry.getUserId(),
            entry.getHabitId(),
            entry.getAchieved()
        );
    }

    public static List<EntryEntity> mapEntryEntities(List<Entry> entries) {
        return entries.stream().map(DBModelMapper::mapEntryEntity).toList();
    }
}
