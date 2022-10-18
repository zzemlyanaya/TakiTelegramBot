package data.providers;

/* created by zzemlyanaya on 14/11/2022 */

import org.junit.jupiter.params.provider.Arguments;
import ru.zzemlyanaya.takibot.data.model.User;

import java.util.stream.Stream;

public class UserTestProvider {

    public static Stream<Arguments> provideTestsUserInsert() {
        return Stream.of(
            Arguments.of(new User(1L, "Stray Kids")),
            Arguments.of(new User(2L, "ATEEZ")),
            Arguments.of(new User(3L, "BTS")),
            Arguments.of(new User(3L, "BTS")),
            Arguments.of(new User(4L, "Le Sserafim"))
        );
    }

    public static Stream<Arguments> provideTestsUserSelect() {
        return Stream.of(
            Arguments.of(1L, new User(1L, "Stray Kids")),
            Arguments.of(1L, new User(1L, "Stray Kids")),
            Arguments.of(2L, new User(2L, "ATEEZ")),
            Arguments.of(3L, new User(3L, "BTS")),
            Arguments.of(4L, new User(4L, "Le Sserafim")),
            Arguments.of(4L, new User(4L, "Le Sserafim"))
        );
    }

    public static Stream<Arguments> provideTestsUserUpdate() {
        return Stream.of(
            Arguments.of(1L, 10L, new User(10L, "Stray Kids")),
            Arguments.of(2L, 20L, new User(20L, "ATEEZ")),
            Arguments.of(20L, 20L, new User(20L, "ATEEZ")),
            Arguments.of(3L, 30L, new User(30L, "BTS")),
            Arguments.of(4L, 40L, new User(40L, "Le Sserafim")),
            Arguments.of(40L, 40L, new User(40L, "Le Sserafim"))
        );
    }

    public static Stream<Arguments> provideTestsUserDelete() {
        return Stream.of(
            Arguments.of(10L),
            Arguments.of(20L),
            Arguments.of(20L),
            Arguments.of(40L)
        );
    }
}
