package data.providers;

import org.junit.jupiter.params.provider.Arguments;
import ru.zzemlyanaya.takibot.data.model.Entry;
import ru.zzemlyanaya.takibot.domain.model.EntryEntity;

import java.time.LocalDate;
import java.util.stream.Stream;

/* created by zzemlyanaya on 26/11/2022 */

public class EntryTestProvider {

    public static Stream<Arguments> provideTestsEntryInsert() {
        return Stream.of(
            Arguments.of(new Entry("2022-11-12", 1L, 1L, 0L)),
            Arguments.of(new Entry("2022-11-12", 1L, 2L, 9L)),
            Arguments.of(new Entry("2022-11-13", 4L, 4L, 0L)),
            Arguments.of(new Entry("2022-11-20", 5L, 3L, 10L))
        );
    }

    public static Stream<Arguments> provideTestsEntrySave() {
        return Stream.of(
            Arguments.of(new EntryEntity(LocalDate.of(2022, 11, 12), 1L, 1L, 0L)),
            Arguments.of(new EntryEntity(LocalDate.of(2022, 11, 12), 1L, 2L, 9L)),
            Arguments.of(new EntryEntity(LocalDate.of(2022, 11, 13), 4L, 4L, 0L)),
            Arguments.of(new EntryEntity(LocalDate.of(2022, 11, 20), 5L, 3L, 10L))
        );
    }

    public static Stream<Arguments> provideTestsEntrySelect() {
        return Stream.of(
            Arguments.of(1L, new Entry("2022-11-12", 1L, 1L, 0L)),
            Arguments.of(2L, new Entry("2022-11-12", 1L, 2L, 9L)),
            Arguments.of(3L, new Entry("2022-11-13", 4L, 4L, 0L)),
            Arguments.of(3L, new Entry("2022-11-13", 4L, 4L, 0L)),
            Arguments.of(4L, new Entry("2022-11-20", 5L, 3L, 10L))
        );
    }

    public static Stream<Arguments> provideTestsEntryUpdate() {
        return Stream.of(
            Arguments.of(1L, 10L, new Entry("2022-11-12", 1L, 1L, 10L)),
            Arguments.of(2L, 19L, new Entry("2022-11-12", 1L, 2L, 19L)),
            Arguments.of(3L, 10L, new Entry("2022-11-13", 4L, 4L, 10L)),
            Arguments.of(3L, 10L, new Entry("2022-11-13", 4L, 4L, 10L)),
            Arguments.of(4L, 110L, new Entry("2022-11-20", 5L, 3L, 110L))
        );
    }

    public static Stream<Arguments> provideTestsEntryDelete() {
        return Stream.of(
            Arguments.of(1L),
            Arguments.of(1L),
            Arguments.of(2L),
            Arguments.of(3L),
            Arguments.of(4L),
            Arguments.of(4L)
        );
    }
}
