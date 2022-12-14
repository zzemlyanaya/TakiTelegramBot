package ru.zzemlyanaya.takibot.data.model;

/* created by zzemlyanaya on 26/10/2022 */


public class Habit {
    private Long id;
    private Long userId;
    private String name;
    private String prompt;
    private Boolean isBinary;
    private int frequency;
    private String metric;
    private double metricGoal;
    private String nextDate;

    public Habit(Long id, Long userId, String name, String prompt, Boolean isBinary, int frequency, String metric, double metricGoal,String nextDate) {
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

    public Habit(Long userId, String name, String prompt, Boolean isBinary, int frequency, String metric, double metricGoal, String nextDate) {
        this.userId = userId;
        this.name = name;
        this.prompt = prompt;
        this.isBinary = isBinary;
        this.frequency = frequency;
        this.metric = metric;
        this.metricGoal = metricGoal;
        this.nextDate = nextDate;
    }

    public Habit(Long id, Long userId, String name, String nextDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.nextDate = nextDate;
    }

    public Habit(Long id, Long userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    public Habit(Long userId, String name, String nextDate) {
        this.userId = userId;
        this.name = name;
        this.nextDate = nextDate;
    }

    public Habit(Long userId, String name) {
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

    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }
}
