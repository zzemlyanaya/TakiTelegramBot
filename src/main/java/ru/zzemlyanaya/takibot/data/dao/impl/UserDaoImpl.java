package ru.zzemlyanaya.takibot.data.dao.impl;

import ru.zzemlyanaya.takibot.core.dao.BaseDao;
import ru.zzemlyanaya.takibot.data.dao.UserDao;
import ru.zzemlyanaya.takibot.data.model.User;

/* created by zzemlyanaya on 04/11/2022 */

public class UserDaoImpl extends BaseDao<UserDao> implements UserDao {

    public UserDaoImpl() {
        super(UserDao.class);
    }

    @Override
    public Integer createTable() {
        return execute(UserDao::createTable);
    }

    @Override
    public Integer dropTable() {
        return execute(UserDao::dropTable);
    }

    @Override
    public User selectById(Long id) {
        return select(userDao -> userDao.selectById(id));
    }

    @Override
    public User selectByPlatformId(Long id) {
        return select(userDao -> userDao.selectByPlatformId(id));

    }

    @Override
    public Integer deleteById(Long id) {
        return execute(userDao -> userDao.deleteById(id));
    }

    @Override
    public Integer deleteByPlatformId(Long id) {
        return execute(userDao -> userDao.deleteByPlatformId(id));
    }

    @Override
    public Integer update(User user) {
        return execute(userDao -> userDao.update(user));
    }

    @Override
    public Integer insert(User user) {
        return execute(userDao -> userDao.insert(user));
    }
}
