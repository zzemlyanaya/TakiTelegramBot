package ru.zzemlyanaya.takibot.domain.model;

/* created by zzemlyanaya on 08/11/2022 */

import java.time.LocalDate;

public class HabitEntity {
    private Long id;
    private Long userId;
    private String name;
    private String prompt;
    private Boolean isBinary;
    private int frequency;
    private String metric;
    private double metricGoal;
    private LocalDate nextDate;

    public HabitEntity(Long id, Long userId, String name, String prompt, Boolean isBinary, int frequency, String metric, double metricGoal, LocalDate nextDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.prompt = prompt;
        this.isBinary = isBinary;
        this.frequency = frequency;
        this.metric = metric;
        this.metricGoal = metricGoal;
        this.nextDate = nextDate;
    }

    public HabitEntity() {
        this.userId = 0L;
        this.name = "";
        this.prompt = "";
        this.isBinary = true;
        this.frequency = 0;
        this.metric = "";
        this.metricGoal = 0d;
        this.nextDate = LocalDate.now();
    }

    public HabitEntity(Long id, Long userId, String name, LocalDate nextDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.nextDate = nextDate;
    }

    public HabitEntity(Long userId, String name, LocalDate nextDate) {
        this.userId = userId;
        this.name = name;
        this.nextDate = nextDate;
    }

    public HabitEntity(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Boolean getIsBinary() {
        return isBinary;
    }

    public void setIsBinary(Boolean binary) {
        isBinary = binary;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public double getMetricGoal() {
        return metricGoal;
    }

    public void setMetricGoal(double metricGoal) {
        this.metricGoal = metricGoal;
    }

    public LocalDate getNextDate() {
        return nextDate;
    }

    public void setNextDate(LocalDate nextDate) {
        this.nextDate = nextDate;
    }
}
