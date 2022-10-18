package data.repository;

/* created by zzemlyanaya on 14/11/2022 */

import data.dao.testImpl.UserDaoTestImpl;
import data.repository.testImpl.UserRepositoryTestImpl;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.zzemlyanaya.takibot.data.dao.UserDao;
import ru.zzemlyanaya.takibot.data.model.User;
import ru.zzemlyanaya.takibot.data.repository.UserRepository;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTests {

    private static final Logger log = LogManager.getLogger();

    private static final UserRepository repo = UserRepositoryTestImpl.INSTANCE;
    private static final UserDao dao = new UserDaoTestImpl();

    private static final CompositeDisposable disposable = new CompositeDisposable();

    @BeforeAll
    public static void createUsersTable() {
        disposable.add(
            repo.createUsersTable().doOnError(log::error).subscribe()
        );
    }

    @AfterAll
    public static void dropUsersTable() throws IOException {
        dao.dropTable();
        disposable.dispose();
    }

    @ParameterizedTest(name = "insert: {index}")
    @MethodSource("data.providers.UserTestProvider#provideTestsUserInsert")
    @Order(1)
    public void saveUser(User user) {
        disposable.add(
            repo.saveUser(user).doOnError(log::error).subscribe()
        );
    }

    @ParameterizedTest(name = "select: {0}")
    @MethodSource("data.providers.UserTestProvider#provideTestsUserSelect")
    @Order(2)
    public void getUserByPlatformId(Long id, User expected) {
        disposable.add(
            repo.getUserByPlatformId(id)
                .doOnError(log::error)
                .subscribe(user -> {
                    assertEquals(expected.getPlatformId(), user.getPlatformId());
                    assertEquals(expected.getUsername(), user.getUsername());
                })
        );
    }

    @ParameterizedTest(name = "update: {0}")
    @MethodSource("data.providers.UserTestProvider#provideTestsUserUpdate")
    @Order(3)
    public void updateUsers(Long oldId, Long newId, User expected) throws IOException {

        User oldUser = dao.selectByPlatformId(oldId);
        oldUser.setPlatformId(newId);

        disposable.add(
            repo.updateUser(oldUser)
                .doOnError(log::error)
                .subscribe(() -> {
                    User newUser = dao.selectByPlatformId(newId);

                    assertEquals(expected.getPlatformId(), newUser.getPlatformId());
                    assertEquals(expected.getUsername(), newUser.getUsername());
                })
        );
    }
}
