package ru.zzemlyanaya.takibot.data.model;

/* created by zzemlyanaya on 31/10/2022 */


public class Entry {
    private Long id;
    private String date;
    private Long userId;
    private Long habitId;
    private double achieved;

    public Entry() {

    }

    public Entry(Long id, String date, Long userId, Long habitId, double achieved) {
        this.id = id;
        this.date = date;
        this.userId = userId;
        this.habitId = habitId;
        this.achieved = achieved;
    }

    public Entry(String date, Long userId, Long habitId, double achieved) {
        this.date = date;
        this.userId = userId;
        this.habitId = habitId;
        this.achieved = achieved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
