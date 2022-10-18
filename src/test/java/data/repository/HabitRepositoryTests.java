package data.repository;

import data.dao.testImpl.HabitDaoTestImpl;
import data.repository.testImpl.HabitRepositoryTestImpl;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.zzemlyanaya.takibot.data.dao.HabitDao;
import ru.zzemlyanaya.takibot.data.model.Habit;
import ru.zzemlyanaya.takibot.data.repository.HabitRepository;
import ru.zzemlyanaya.takibot.domain.model.HabitEntity;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/* created by zzemlyanaya on 08/11/2022 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HabitRepositoryTests {

    private static final Logger log = LogManager.getLogger();

    private static final HabitRepository repo = HabitRepositoryTestImpl.INSTANCE;
    private static final HabitDao dao = new HabitDaoTestImpl();

    private static final CompositeDisposable disposable = new CompositeDisposable();

    @BeforeAll
    public static void createHabitsTable() {
        disposable.add(
            repo.createHabitsTable().doOnError(log::error).subscribe()
        );
    }

    @AfterAll
    public static void dropUsersTable() throws IOException {
        dao.dropTable();
        disposable.dispose();
    }

    @ParameterizedTest(name = "insert: {index}")
    @MethodSource("data.providers.HabitTestProvider#provideTestsForHabitSave")
    @Order(1)
    public void saveHabit(HabitEntity habit) {
        disposable.add(
            repo.saveHabit(habit).doOnError(log::error).subscribe()
        );
    }

    @ParameterizedTest(name = "select: {0}")
    @MethodSource("data.providers.HabitTestProvider#provideTestsHabitSelect")
    @Order(2)
    public void getHabitById(Long id, Habit expected) {
        disposable.add(
            repo.getHabitById(id)
                .doOnError(log::error)
                .subscribe(habit -> {
                    assertEquals(expected.getUserId(), habit.getUserId());
                    assertEquals(expected.getName(), habit.getName());
                })
        );
    }

    @ParameterizedTest(name = "select by user: {0}")
    @MethodSource("data.providers.HabitTestProvider#provideTestsHabitSelectByUserId")
    @Order(3)
    public void getHabitsByUserId(Long userId, List<Habit> expected) {
        disposable.add(
            repo.getHabitsByUserId(userId)
                .doOnError(log::error)
                .subscribe(habits -> {
                    for (int i = 0; i < habits.size(); i++) {
                        assertEquals(expected.get(i).getName(), habits.get(i).getName());
                    }
                })
        );
    }
}
