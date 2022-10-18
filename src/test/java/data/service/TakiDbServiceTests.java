package data.service;

import data.dao.testImpl.HabitDaoTestImpl;
import data.dao.testImpl.UserDaoTestImpl;
import data.service.testImpl.TakiDbServiceTestImpl;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.zzemlyanaya.takibot.data.dao.HabitDao;
import ru.zzemlyanaya.takibot.data.dao.UserDao;
import ru.zzemlyanaya.takibot.data.model.Habit;
import ru.zzemlyanaya.takibot.data.model.User;
import ru.zzemlyanaya.takibot.domain.model.HabitEntity;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* created by zzemlyanaya on 08/11/2022 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TakiDbServiceTests {

    private static final Logger log = LogManager.getLogger();

    private static final TakiDbServiceTestImpl service = TakiDbServiceTestImpl.INSTANCE;
    private static final HabitDao habitDao = new HabitDaoTestImpl();
    private static final UserDao userDao = new UserDaoTestImpl();

    private static final CompositeDisposable disposable = new CompositeDisposable();

    @BeforeAll
    public static void initDb() {
        disposable.add(
            service.initDb().doOnError(log::error).subscribe()
        );
    }

    @AfterAll
    public static void dropUsersTable() throws IOException {
        userDao.dropTable();
        habitDao.dropTable();
        disposable.dispose();
    }

    // ------ Users ------

    @ParameterizedTest(name = "user insert: {index}")
    @MethodSource("data.providers.UserTestProvider#provideTestsUserInsert")
    @Order(1)
    public void saveUser(User user) {
        disposable.add(
            service.saveUser(user.getPlatformId(), user.getUsername()).doOnError(log::error).subscribe()
        );
    }

    @ParameterizedTest(name = "user select: {0}")
    @MethodSource("data.providers.UserTestProvider#provideTestsUserSelect")
    @Order(2)
    public void getUserByPlatformId(Long id, User expected) {
        disposable.add(
            service.getUserByPlatformId(id)
                .doOnError(log::error)
                .subscribe(user -> {
                    assertEquals(expected.getPlatformId(), user.getPlatformId());
                    assertEquals(expected.getUsername(), user.getUsername());
                })
        );
    }

    @ParameterizedTest(name = "user update: {0}")
    @MethodSource("data.providers.UserTestProvider#provideTestsUserUpdate")
    @Order(3)
    public void updateUsers(Long oldId, Long newId, User expected) throws IOException {

        User oldUser = userDao.selectByPlatformId(oldId);
        oldUser.setPlatformId(newId);

        disposable.add(
            service.updateUser(oldUser)
                .doOnError(log::error)
                .subscribe(() -> {
                    User newUser = userDao.selectByPlatformId(newId);

                    assertEquals(expected.getPlatformId(), newUser.getPlatformId());
                    assertEquals(expected.getUsername(), newUser.getUsername());
                })
        );
    }

    // ------ Habits ------

    @ParameterizedTest(name = "habit insert: {index}")
    @MethodSource("data.providers.HabitTestProvider#provideTestsForHabitSave")
    @Order(4)
    public void saveHabit(HabitEntity habit) {
        disposable.add(
            service.saveHabit(habit).doOnError(log::error).subscribe()
        );
    }

    @ParameterizedTest(name = "habit select: {0}")
    @MethodSource("data.providers.HabitTestProvider#provideTestsHabitSelect")
    @Order(5)
    public void getHabitById(Long id, Habit expected) {
        disposable.add(
            service.getHabitById(id)
                .doOnError(log::error)
                .subscribe(habit -> {
                    assertEquals(expected.getUserId(), habit.getUserId());
                    assertEquals(expected.getName(), habit.getName());
                })
        );
    }

    @ParameterizedTest(name = "habit select by user: {0}")
    @MethodSource("data.providers.HabitTestProvider#provideTestsHabitSelectByUserId")
    @Order(6)
    public void getHabitsByUserId(Long userId, List<Habit> expected) {
        disposable.add(
            service.getHabitsByUserId(userId)
                .doOnError(log::error)
                .subscribe(habits -> {
                    for (int i = 0; i < habits.size(); i++) {
                        assertEquals(expected.get(i).getName(), habits.get(i).getName());
                    }
                })
        );
    }
}
