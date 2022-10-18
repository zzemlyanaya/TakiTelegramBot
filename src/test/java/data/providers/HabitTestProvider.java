package data.providers;

/* created by zzemlyanaya on 14/11/2022 */

import org.junit.jupiter.params.provider.Arguments;
import ru.zzemlyanaya.takibot.data.model.Habit;
import ru.zzemlyanaya.takibot.domain.model.HabitEntity;

import java.util.List;
import java.util.stream.Stream;

public class HabitTestProvider {

    public static Stream<Arguments> provideTestsForHabitInsert() {
        return Stream.of(
            Arguments.of(new Habit(1L, "Stray Kids habit")),
            Arguments.of(new Habit(1L, "StRaY KiDs HaBiT")),
            Arguments.of(new Habit(2L, "ATEEZ habit")),
            Arguments.of(new Habit(3L, "BTS habit")),
            Arguments.of(new Habit(3L, "BTS habit")),
            Arguments.of(new Habit(4L, "Le Sserafim habit"))
        );
    }

    public static Stream<Arguments> provideTestsForHabitSave() {
        return Stream.of(
            Arguments.of(new HabitEntity(1L, "Stray Kids habit")),
            Arguments.of(new HabitEntity(1L, "StRaY KiDs HaBiT")),
            Arguments.of(new HabitEntity(2L, "ATEEZ habit")),
            Arguments.of(new HabitEntity(3L, "BTS habit")),
            Arguments.of(new HabitEntity(3L, "BTS habit")),
            Arguments.of(new HabitEntity(4L, "Le Sserafim habit"))
        );
    }

    public static Stream<Arguments> provideTestsHabitSelect() {
        return Stream.of(
            Arguments.of(1L, new Habit(1L, "Stray Kids habit")),
            Arguments.of(1L, new Habit(1L, "Stray Kids habit")),
            Arguments.of(3L, new Habit(2L, "ATEEZ habit")),
            Arguments.of(4L, new Habit(3L, "BTS habit")),
            Arguments.of(5L, new Habit(3L, "BTS habit")),
            Arguments.of(6L, new Habit(4L, "Le Sserafim habit")),
            Arguments.of(6L, new Habit(4L, "Le Sserafim habit"))
        );
    }

    public static Stream<Arguments> provideTestsHabitSelectByUserId() {
        return Stream.of(
            Arguments.of(1L, List.of(
                new Habit(1L, 1L, "Stray Kids habit"),
                new Habit(2L, 1L, "StRaY KiDs HaBiT")
            )),
            Arguments.of(2L, List.of(new Habit(3L, 2L, "ATEEZ habit"))),
            Arguments.of(3L, List.of(
                new Habit(4L, 3L, "BTS habit"),
                new Habit(5L, 3L, "BTS habit")
            )),
            Arguments.of(4L, List.of(new Habit(6L, 4L, "Le Sserafim habit"))),
            Arguments.of(4L, List.of(new Habit(6L, 4L, "Le Sserafim habit")))
        );
    }

    public static Stream<Arguments> provideTestsHabitDelete() {
        return Stream.of(
            Arguments.of(1L),
            Arguments.of(2L),
            Arguments.of(2L),
            Arguments.of(4L)
        );
    }
}
