package ru.zzemlyanaya.takibot.data.repository.impl;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.zzemlyanaya.takibot.data.dao.UserDao;
import ru.zzemlyanaya.takibot.data.dao.impl.UserDaoImpl;
import ru.zzemlyanaya.takibot.data.model.User;
import ru.zzemlyanaya.takibot.data.repository.UserRepository;
import ru.zzemlyanaya.takibot.domain.mapper.DBModelMapper;
import ru.zzemlyanaya.takibot.domain.model.UserEntity;

/* created by zzemlyanaya on 04/11/2022 */

public class UserRepositoryImpl implements UserRepository {

    public static final UserRepositoryImpl INSTANCE = new UserRepositoryImpl();

    private final UserDao userDao = new UserDaoImpl();

    @Override
    public Completable createUsersTable() {
        return Completable.fromCallable(userDao::createTable);
    }

    @Override
    public Single<UserEntity> getUserByPlatformId(Long id) {
        return Single.fromCallable(() ->
            DBModelMapper.mapUserEntity(userDao.selectByPlatformId(id))
        );
    }

    @Override
    public Completable saveUser(User user) {
        return Completable.fromCallable(() -> userDao.insert(user));
    }

    @Override
    public Completable updateUser(User user) {
        return Completable.fromCallable(() -> userDao.update(user));
    }
}
