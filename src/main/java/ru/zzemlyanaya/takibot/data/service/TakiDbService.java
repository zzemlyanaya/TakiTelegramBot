package ru.zzemlyanaya.takibot.data.service;

/* created by zzemlyanaya on 08/11/2022 */

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.zzemlyanaya.takibot.data.model.User;
import ru.zzemlyanaya.takibot.domain.model.CheckModel;
import ru.zzemlyanaya.takibot.domain.model.EntryEntity;
import ru.zzemlyanaya.takibot.domain.model.HabitEntity;
import ru.zzemlyanaya.takibot.domain.model.UserEntity;

import java.time.LocalDate;
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
    Single<List<HabitEntity>> getHabitsByUserAndDate(Long id, LocalDate date);
    Completable saveHabit(HabitEntity habit);

    // Entries
    Completable saveEntry(EntryEntity entry);
    Completable updateEntry(EntryEntity entry);

    // Transactions
    Completable runCheckTransaction(CheckModel checkModel);
}
