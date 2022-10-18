package ru.zzemlyanaya.takibot.data.service.impl;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.zzemlyanaya.takibot.data.model.User;
import ru.zzemlyanaya.takibot.data.repository.HabitRepository;
import ru.zzemlyanaya.takibot.data.repository.UserRepository;
import ru.zzemlyanaya.takibot.data.repository.impl.HabitRepositoryImpl;
import ru.zzemlyanaya.takibot.data.repository.impl.UserRepositoryImpl;
import ru.zzemlyanaya.takibot.data.service.TakiDbService;
import ru.zzemlyanaya.takibot.domain.model.HabitEntity;
import ru.zzemlyanaya.takibot.domain.model.UserEntity;

import java.util.List;

/* created by zzemlyanaya on 08/11/2022 */

public class TakiDbServiceImpl implements TakiDbService {

    public static final TakiDbServiceImpl INSTANCE = new TakiDbServiceImpl();

    private final UserRepository userRepository = UserRepositoryImpl.INSTANCE;
    private final HabitRepository habitRepository = HabitRepositoryImpl.INSTANCE;

    @Override
    public Completable initDb() {
        return Completable.mergeArray(
            userRepository.createUsersTable(),
            habitRepository.createHabitsTable()
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
    public Completable saveHabit(HabitEntity habit) {
        return habitRepository.saveHabit(habit);
    }
}
