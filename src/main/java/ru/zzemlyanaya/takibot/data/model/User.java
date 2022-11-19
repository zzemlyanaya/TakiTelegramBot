package ru.zzemlyanaya.takibot.data.model;

/* created by zzemlyanaya on 26/10/2022 */

public class User {
    private Long id;
    private Long platformId;
    private String username;

    public User(Long id, Long platformId, String username) {
        this.id = id;
        this.platformId = platformId;
        this.username = username;
    }

    public User(Long platformId, String username) {
        this.platformId = platformId;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
