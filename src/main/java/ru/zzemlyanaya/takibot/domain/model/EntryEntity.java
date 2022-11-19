package ru.zzemlyanaya.takibot.domain.model;

/* created by zzemlyanaya on 31/10/2022 */


import java.time.LocalDate;

public class EntryEntity {
    private LocalDate date;
    private Long userId;
    private Long habitId;
    private double achieved;

    public EntryEntity(LocalDate date, Long userId, Long habitId, double achieved) {
        this.date = date;
        this.userId = userId;
        this.habitId = habitId;
        this.achieved = achieved;
    }

    public EntryEntity(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHabitId() {
        return habitId;
    }

    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    public double getAchieved() {
        return achieved;
    }

    public void setAchieved(double achieved) {
        this.achieved = achieved;
    }
}
