package data.repository.testImpl;

import data.dao.testImpl.EntryDaoTestImpl;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.zzemlyanaya.takibot.data.dao.EntryDao;
import ru.zzemlyanaya.takibot.data.repository.EntryRepository;
import ru.zzemlyanaya.takibot.domain.mapper.DBModelMapper;
import ru.zzemlyanaya.takibot.domain.model.EntryEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/* created by zzemlyanaya on 04/11/2022 */

public class EntryRepositoryTestImpl implements EntryRepository {

    public static final EntryRepositoryTestImpl INSTANCE = new EntryRepositoryTestImpl();

    private final EntryDao entryDao = new EntryDaoTestImpl();

    @Override
    public Completable createEntriesTable() {
        return Completable.fromCallable(entryDao::createTable);
    }

    @Override
    public Completable saveEntry(EntryEntity entry) {
        return Completable.fromCallable(() -> entryDao.insert(DBModelMapper.mapEntryModel(entry)));
    }

    @Override
    public Completable updateEntry(EntryEntity entry) {
        return Completable.fromCallable(() -> entryDao.update(DBModelMapper.mapEntryModel(entry)));
    }

    @Override
    public Single<List<EntryEntity>> getTodayEntries(Long userId) {
        return Single.fromCallable(() -> DBModelMapper.mapEntryEntities(
            entryDao.selectUserDay(userId, LocalDate.now().format(DateTimeFormatter.ISO_DATE))
        ));
    }

    @Override
    public Single<List<EntryEntity>> getEntriesBetween(Long userId, LocalDate startDate, LocalDate endDate) {
        return Single.fromCallable(() -> DBModelMapper.mapEntryEntities(
                entryDao.selectUserBetween(
                    userId,
                    startDate.format(DateTimeFormatter.ISO_DATE),
                    endDate.format(DateTimeFormatter.ISO_DATE))
            )
        );
    }
}
