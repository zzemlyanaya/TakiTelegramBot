package data.dao;/* created by zzemlyanaya on 04/11/2022 */

import data.dao.testImpl.UserDaoTestImpl;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.zzemlyanaya.takibot.data.dao.UserDao;
import ru.zzemlyanaya.takibot.data.model.User;
import ru.zzemlyanaya.takibot.data.session.SqlSessionUtil;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDaoTests {

    private static final SqlSessionUtil sqlSessionUtil = new SqlSessionUtil("test");

    private static final UserDao dao = new UserDaoTestImpl();

    @BeforeAll
    public static void createUsersTable() throws IOException {
        dao.createTable();
    }

    @AfterAll
    public static void dropUsersTable() throws IOException {
        dao.dropTable();
    }

    @Test
    @Order(1)
    public void testSqlConnection() throws NullPointerException {
        SqlSession sqlSession = sqlSessionUtil.getSqlSession();
        System.out.println("Test MyBatis linked database driver:" + sqlSession.toString());
    }

    @ParameterizedTest(name = "insert: {index}")
    @MethodSource("data.providers.UserTestProvider#provideTestsUserInsert")
    @Order(2)
    public void insertUsers(User user) throws IOException {
        dao.insert(user);
    }

    @ParameterizedTest(name = "select: {0}")
    @MethodSource("data.providers.UserTestProvider#provideTestsUserSelect")
    @Order(3)
    public void selectUsers(Long id, User expected) throws IOException {
        User user = dao.selectByPlatformId(id);
        System.out.println(user.toString());

        assertEquals(expected.getPlatformId(), user.getPlatformId());
        assertEquals(expected.getUsername(), user.getUsername());
    }

    @ParameterizedTest(name = "update: {0}")
    @MethodSource("data.providers.UserTestProvider#provideTestsUserUpdate")
    @Order(4)
    public void updateUsers(Long oldId, Long newId, User expected) throws IOException {
        User user = dao.selectByPlatformId(oldId);
        user.setPlatformId(newId);
        dao.update(user);

        user = dao.selectByPlatformId(newId);

        assertEquals(expected.getPlatformId(), user.getPlatformId());
        assertEquals(expected.getUsername(), user.getUsername());
    }

    @ParameterizedTest(name = "delete: {0}")
    @MethodSource("data.providers.UserTestProvider#provideTestsUserDelete")
    @Order(5)
    public void deleteUsers(Long id) throws IOException {
        dao.deleteByPlatformId(id);
        User user = dao.selectByPlatformId(id);

        assertNull(user);
    }

}
