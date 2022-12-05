package ru.zzemlyanaya.takibot.data.service.impl;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.zzemlyanaya.takibot.data.model.User;
import ru.zzemlyanaya.takibot.data.repository.EntryRepository;
import ru.zzemlyanaya.takibot.data.repository.HabitRepository;
import ru.zzemlyanaya.takibot.data.repository.UserRepository;
import ru.zzemlyanaya.takibot.data.repository.impl.EntryRepositoryImpl;
import ru.zzemlyanaya.takibot.data.repository.impl.HabitRepositoryImpl;
import ru.zzemlyanaya.takibot.data.repository.impl.UserRepositoryImpl;
import ru.zzemlyanaya.takibot.data.service.TakiDbService;
import ru.zzemlyanaya.takibot.domain.model.CheckModel;
import ru.zzemlyanaya.takibot.domain.model.EntryEntity;
import ru.zzemlyanaya.takibot.domain.model.HabitEntity;
import ru.zzemlyanaya.takibot.domain.model.UserEntity;

import java.time.LocalDate;
import java.util.List;

/* created by zzemlyanaya on 08/11/2022 */

public class TakiDbServiceImpl implements TakiDbService {

    public static final TakiDbServiceImpl INSTANCE = new TakiDbServiceImpl();

    private final UserRepository userRepository = UserRepositoryImpl.INSTANCE;
    private final HabitRepository habitRepository = HabitRepositoryImpl.INSTANCE;
    private final EntryRepository entryRepository = EntryRepositoryImpl.INSTANCE;

    @Override
    public Completable initDb() {
        return Completable.mergeArray(
            userRepository.createUsersTable(),
            habitRepository.createHabitsTable(),
            entryRepository.createEntriesTable()
        );
    }

    // ------ Users ------
    @Override
    public Single<UserEntity> getUserByPlatformId(Long id) {
        return userRepository.getUserByPlatformId(id);
    }

    @Override
    public Completable updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    public Completable saveUser(Long platformId, String name) {
        return userRepository.saveUser(new User(platformId, name));
    }

    // ------ Habits ------
    @Override
    public Single<HabitEntity> getHabitById(Long id) {
        return habitRepository.getHabitById(id);
    }

    @Override
    public Single<List<HabitEntity>> getHabitsByUserId(Long id) {
        return habitRepository.getHabitsByUserId(id);
    }

    @Override
    public Single<List<HabitEntity>> getHabitsByUserAndDate(Long id, LocalDate date) {
        return habitRepository.getHabitsByUserAndDate(id, date);
    }

    @Override
    public Completable saveHabit(HabitEntity habit) {
        return habitRepository.saveHabit(habit);
    }

    private Completable setNextDate(HabitEntity habit) {
        LocalDate nextDate = habit.getNextDate().plusDays(habit.getFrequency());
        return habitRepository.setNextDate(habit.getId(), nextDate);
    }

    // ------ Entries ------

    @Override
    public Completable saveEntry(EntryEntity entry) {
        return entryRepository.saveEntry(entry);
    }

    @Override
    public Completable updateEntry(EntryEntity entry) {
        return entryRepository.updateEntry(entry);
    }

    @Override
    public Single<List<EntryEntity>> getTodayEntries(Long userId) {
        return entryRepository.getTodayEntries(userId);
    }

    @Override
    public Single<List<EntryEntity>> getEntriesBetween(Long userId, LocalDate startDate, LocalDate endDate) {
        return entryRepository.getEntriesBetween(userId, startDate, endDate);
    }

    // ------ Transactions ------

    public Completable runCheckTransaction(CheckModel checkModel) {
        return Completable.mergeArray(
            saveEntry(checkModel.getEntry()),
            setNextDate(checkModel.getHabit())
        );
    }
}
