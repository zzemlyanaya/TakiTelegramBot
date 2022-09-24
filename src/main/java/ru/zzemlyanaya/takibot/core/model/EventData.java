package ru.zzemlyanaya.takibot.core.model;

/* created by zzemlyanaya on 24/09/2022 */

public class EventData<T> {
    private final T data;

    public EventData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
