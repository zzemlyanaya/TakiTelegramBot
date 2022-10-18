package ru.zzemlyanaya.takibot.data.model;

/* created by zzemlyanaya on 31/10/2022 */


import java.time.LocalDate;

public class Day {
    Long id;
    LocalDate date;
    Long userId;
    Long habitId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
