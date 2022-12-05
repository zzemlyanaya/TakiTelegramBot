package ru.zzemlyanaya.takibot.domain.model;

/* created by zzemlyanaya on 04/12/2022 */

public class HabitUiModel {
    private Long id;
    private String displayName;
    private String progressLine;

    public HabitUiModel(Long id, String displayName, String progressLine) {
        this.id = id;
        this.displayName = displayName;
        this.progressLine = progressLine;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProgressLine() {
        return progressLine;
    }

    public void setProgressLine(String progressLine) {
        this.progressLine = progressLine;
    }
}
