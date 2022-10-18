package ru.zzemlyanaya.takibot.data.repository;

/* created by zzemlyanaya on 04/11/2022 */

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.zzemlyanaya.takibot.data.model.User;
import ru.zzemlyanaya.takibot.domain.model.UserEntity;

public interface UserRepository {

    Completable createUsersTable();

    Single<UserEntity> getUserByPlatformId(Long id);

    Completable saveUser(User user);

    Completable updateUser(User user);
}
