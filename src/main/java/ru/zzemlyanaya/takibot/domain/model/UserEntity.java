package ru.zzemlyanaya.takibot.domain.model;

/* created by zzemlyanaya on 08/11/2022 */

public class UserEntity {
    private Long platformId;
    private String username;

    public UserEntity(Long platformId, String username) {
        this.platformId = platformId;
        this.username = username;
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
