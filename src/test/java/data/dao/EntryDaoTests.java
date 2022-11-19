package data.dao;

/* created by zzemlyanaya on 26/11/2022 */

import data.dao.testImpl.EntryDaoTestImpl;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.zzemlyanaya.takibot.data.dao.EntryDao;
import ru.zzemlyanaya.takibot.data.model.Entry;
import ru.zzemlyanaya.takibot.data.session.SqlSessionUtil;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntryDaoTests {

    private static final SqlSessionUtil sqlSessionUtil = new SqlSessionUtil("test");

    private static final EntryDao dao = new EntryDaoTestImpl();

    @BeforeAll
    public static void createEntriesTable() throws IOException {
        dao.createTable();
    }

    @AfterAll
    public static void dropEntriesTable() throws IOException {
        dao.dropTable();
    }

    @Test
    @Order(1)
    public void testSqlConnection() throws NullPointerException {
        SqlSession sqlSession = sqlSessionUtil.getSqlSession();
        System.out.println("Test MyBatis linked database driver:" + sqlSession.toString());
    }

    @ParameterizedTest(name = "insert: {index}")
    @MethodSource("data.providers.EntryTestProvider#provideTestsEntryInsert")
    @Order(2)
    public void insertEntries(Entry entry) throws IOException {
        dao.insert(entry);
    }

    @ParameterizedTest(name = "select: {0}")
    @MethodSource("data.providers.EntryTestProvider#provideTestsEntrySelect")
    @Order(3)
    public void selectEntries(Long id, Entry expected) throws IOException {
        Entry entry = dao.selectById(id);
        System.out.println(entry.toString());

        assertEquals(expected.getUserId(), entry.getUserId());
        assertEquals(expected.getHabitId(), entry.getHabitId());
        assertEquals(expected.getAchieved(), entry.getAchieved());
    }

    @ParameterizedTest(name = "update: {0}")
    @MethodSource("data.providers.EntryTestProvider#provideTestsEntryUpdate")
    @Order(4)
    public void updateEntries(Long id, Long newAchieved, Entry expected) throws IOException {
        Entry entry = dao.selectById(id);
        entry.setAchieved(newAchieved);
        dao.update(entry);

        entry = dao.selectById(id);

        assertEquals(expected.getDate(), entry.getDate());
        assertEquals(expected.getAchieved(), entry.getAchieved());
    }

    @ParameterizedTest(name = "delete: {0}")
    @MethodSource("data.providers.EntryTestProvider#provideTestsEntryDelete")
    @Order(5)
    public void deleteEntries(Long id) throws IOException {
        dao.deleteById(id);
        Entry entry = dao.selectById(id);

        assertNull(entry);
    }
}
