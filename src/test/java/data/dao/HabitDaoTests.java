package data.dao;/* created by zzemlyanaya on 04/11/2022 */

import data.dao.testImpl.HabitDaoTestImpl;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.zzemlyanaya.takibot.data.dao.HabitDao;
import ru.zzemlyanaya.takibot.data.model.Habit;
import ru.zzemlyanaya.takibot.data.session.SqlSessionUtil;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HabitDaoTests {

    private static final SqlSessionUtil sqlSessionUtil = new SqlSessionUtil("test");

    private static final HabitDao dao = new HabitDaoTestImpl();

    @BeforeAll
    public static void createHabitsTable() throws IOException {
        dao.createTable();
    }

    @AfterAll
    public static void dropHabitsTable() throws IOException {
        dao.dropTable();
    }

    @Test
    @Order(1)
    public void testSqlConnection() throws NullPointerException {
        SqlSession sqlSession = sqlSessionUtil.getSqlSession();
        System.out.println("Test MyBatis linked database driver:" + sqlSession.toString());
    }

    @ParameterizedTest(name = "insert: {index}")
    @MethodSource("data.providers.HabitTestProvider#provideTestsForHabitInsert")
    @Order(2)
    public void insertHabits(Habit habit) throws IOException {
        dao.insert(habit);
    }

    @ParameterizedTest(name = "select: {0}")
    @MethodSource("data.providers.HabitTestProvider#provideTestsHabitSelect")
    @Order(3)
    public void selectHabits(Long id, Habit expected) throws IOException {
        Habit habit = dao.selectById(id);
        System.out.println(habit.toString());

        assertEquals(expected.getUserId(), habit.getUserId());
        assertEquals(expected.getName(), habit.getName());
    }

    @ParameterizedTest(name = "select by user: {0}")
    @MethodSource("data.providers.HabitTestProvider#provideTestsHabitSelectByUserId")
    @Order(4)
    public void selectHabitsByUserId(Long userId, List<Habit> expected) throws IOException {
        List<Habit> habits = dao.selectByUserId(userId);
        System.out.println(habits.toString());

        for (int i = 0; i < habits.size(); i++) {
            assertEquals(expected.get(i).getId(), habits.get(i).getId());
            assertEquals(expected.get(i).getName(), habits.get(i).getName());
        }
    }

    @ParameterizedTest(name = "select by date: {0}")
    @MethodSource("data.providers.HabitTestProvider#provideTestsHabitSelectByUserAndDate")
    @Order(5)
    public void selectHabitsByUserAndDate(Long id, String date, List<Habit> expected) throws IOException {
        List<Habit> habits = dao.selectByUserAndDate(id, date);
        System.out.println(habits.toString());

        for (int i = 0; i < habits.size(); i++) {
            assertEquals(expected.get(i).getUserId(), habits.get(i).getUserId());
            assertEquals(expected.get(i).getName(), habits.get(i).getName());
            assertEquals(expected.get(i).getNextDate(), habits.get(i).getNextDate());
        }
    }

    @ParameterizedTest(name = "delete: {0}")
    @MethodSource("data.providers.HabitTestProvider#provideTestsHabitDelete")
    @Order(6)
    public void deleteHabits(Long id) throws IOException {
        dao.deleteById(id);
        Habit habit = dao.selectById(id);

        assertNull(habit);
    }

}
