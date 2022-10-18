package ru.zzemlyanaya.takibot.data.dao;

/* created by zzemlyanaya on 04/11/2022 */

import ru.zzemlyanaya.takibot.data.model.User;

public interface UserDao {

    Integer createTable();
    Integer dropTable();

    User selectById(Long id);
    User selectByPlatformId(Long id);
    Integer deleteById(Long id);
    Integer deleteByPlatformId(Long id);

    Integer update(User user);
    Integer insert(User user);

}
