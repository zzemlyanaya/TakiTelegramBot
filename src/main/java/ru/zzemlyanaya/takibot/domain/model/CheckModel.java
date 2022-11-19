package ru.zzemlyanaya.takibot.domain.model;

/* created by zzemlyanaya on 28/11/2022 */

import java.time.LocalDate;

public class CheckModel {
    private HabitEntity habit;
    private EntryEntity entry;

    public CheckModel(HabitEntity habit, EntryEntity entry) {
        this.habit = habit;
        this.entry = entry;
    }

    public CheckModel() {
        entry = new EntryEntity(LocalDate.now());
    }

    public HabitEntity getHabit() {
        return habit;
    }

    public void setHabit(HabitEntity habit) {
        this.habit = habit;
        entry.setHabitId(habit.getId());
        entry.setUserId(habit.getUserId());
    }

    public EntryEntity getEntry() {
        return entry;
    }

    public void setAchieved(double data) {
        entry.setAchieved(data);
    }
}
