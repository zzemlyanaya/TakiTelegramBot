/* created by zzemlyanaya on 26/09/2022 */

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.zzemlyanaya.takibot.core.model.EventData;
import ru.zzemlyanaya.takibot.domain.handlers.ConsoleReader;
import ru.zzemlyanaya.takibot.domain.handlers.ConsoleWriter;
import ru.zzemlyanaya.takibot.domain.handlers.MessageEventHandler;
import ru.zzemlyanaya.takibot.domain.model.MessageData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IOHandlersTests {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream systemOut = System.out;
    private static final InputStream systemIn = System.in;

    @BeforeAll
    public static void setupIOStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    public static void restoreIOStreams() {
        System.setOut(systemOut);
        System.setIn(systemIn);
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideTestsForReader")
    public void testReader(String input, String expectedResult) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ConsoleReader consoleReader = new ConsoleReader();

        assertEquals(expectedResult, consoleReader.handle());
    }

    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("provideTestForMessageHandler")
    public void testMessageHandler(String input, EventData<MessageData> expectedResult) {

        MessageEventHandler messageHandler = new MessageEventHandler();

        assertEquals(expectedResult.getData().getMessage(), messageHandler.handle(input).getData().getMessage());
    }

    @ParameterizedTest(name = "{index}: {1}")
    @MethodSource("provideTestsForWriter")
    public void testWriter(EventData<MessageData> data, String expectedResult) {
        outContent.reset();

        ConsoleWriter consoleWriter = new ConsoleWriter();

        consoleWriter.handle(data);
        assertEquals(expectedResult, outContent.toString());
    }

    private static Stream<Arguments> provideTestsForReader() {
        return Stream.of(
                Arguments.of("Hi", "Hi"),
                Arguments.of("Boring test", "Boring test"),
                Arguments.of("SKZ <3", "SKZ <3"),
                Arguments.of("Case 143", "Case 143")
        );
    }

    private static Stream<Arguments> provideTestForMessageHandler() {
        return Stream.of(
                Arguments.of("Hi", new EventData<>(new MessageData("Received: Hi\n"))),
                Arguments.of("Boring test", new EventData<>(new MessageData("Received: Boring test\n"))),
                Arguments.of("SKZ <3", new EventData<>(new MessageData("Received: SKZ <3\n"))),
                Arguments.of("Case 143", new EventData<>(new MessageData("Received: Case 143\n")))
        );
    }

    private static Stream<Arguments> provideTestsForWriter() {
        return Stream.of(
                Arguments.of(new EventData<>(new MessageData("Received: Hi\n")), "Received: Hi\n"),
                Arguments.of(new EventData<>(new MessageData("Received: Boring test\n")), "Received: Boring test\n"),
                Arguments.of(new EventData<>(new MessageData("Received: SKZ <3\n")), "Received: SKZ <3\n"),
                Arguments.of(new EventData<>(new MessageData("Received: Case 143\n")), "Received: Case 143\n")
        );
    }
}
