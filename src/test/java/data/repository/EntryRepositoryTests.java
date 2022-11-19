package data.repository;

/* created by zzemlyanaya on 26/11/2022 */

import data.dao.testImpl.EntryDaoTestImpl;
import data.repository.testImpl.EntryRepositoryTestImpl;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.zzemlyanaya.takibot.data.dao.EntryDao;
import ru.zzemlyanaya.takibot.data.model.Entry;
import ru.zzemlyanaya.takibot.data.repository.EntryRepository;
import ru.zzemlyanaya.takibot.domain.mapper.DBModelMapper;
import ru.zzemlyanaya.takibot.domain.model.EntryEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntryRepositoryTests {

    private static final Logger log = LogManager.getLogger();

    private static final EntryRepository repo = EntryRepositoryTestImpl.INSTANCE;
    private static final EntryDao dao = new EntryDaoTestImpl();

    private static final CompositeDisposable disposable = new CompositeDisposable();

    @BeforeAll
    public static void createEntriesTable() throws IOException {
        disposable.add(
            repo.createEntriesTable().doOnError(log::error).subscribe()
        );
    }

    @AfterAll
    public static void dropEntriesTable() throws IOException {
        dao.dropTable();
        disposable.dispose();
    }

    @ParameterizedTest(name = "insert: {index}")
    @MethodSource("data.providers.EntryTestProvider#provideTestsEntrySave")
    @Order(1)
    public void insertEntries(EntryEntity entry) throws IOException {
        disposable.add(
            repo.saveEntry(entry).doOnError(log::error).subscribe()
        );
    }

    @ParameterizedTest(name = "update: {0}")
    @MethodSource("data.providers.EntryTestProvider#provideTestsEntryUpdate")
    @Order(2)
    public void updateEntries(Long id, Long newAchieved, Entry expected) throws IOException {
        EntryEntity entry = DBModelMapper.mapEntryEntity(dao.selectById(id));
        entry.setAchieved(newAchieved);

        disposable.add(
            repo.updateEntry(entry)
                .doOnError(log::error)
                .subscribe(() -> {
                    Entry newEntry = dao.selectById(id);

                    assertEquals(expected.getDate(), newEntry.getDate());
                    assertEquals(expected.getAchieved(), newEntry.getAchieved());
                })
        );
    }
}
