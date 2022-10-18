package ru.zzemlyanaya.takibot.data.service;

/* created by zzemlyanaya on 08/11/2022 */

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.zzemlyanaya.takibot.data.model.User;
import ru.zzemlyanaya.takibot.domain.model.HabitEntity;
import ru.zzemlyanaya.takibot.domain.model.UserEntity;

import java.util.List;

public interface TakiDbService {

    // Common
    Completable initDb();

    // Users
    Single<UserEntity> getUserByPlatformId(Long id);
    Completable updateUser(User user);
    Completable saveUser(Long platformId, String name);

    // Habits
    Single<HabitEntity> getHabitById(Long id);
    Single<List<HabitEntity>> getHabitsByUserId(Long id);
    Completable saveHabit(HabitEntity habit);
}
