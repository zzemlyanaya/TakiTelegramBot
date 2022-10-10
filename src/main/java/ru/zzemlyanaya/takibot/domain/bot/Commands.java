package ru.zzemlyanaya.takibot.domain.bot;

/* created by zzemlyanaya on 10/10/2022 */

public enum Commands {
    START ("start"),
    STOP("stop"),
    ADD("add"),
    CHECK("check"),
    TODAY("today"),
    STATISTIC("statistic");

    private final String name;

    Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
