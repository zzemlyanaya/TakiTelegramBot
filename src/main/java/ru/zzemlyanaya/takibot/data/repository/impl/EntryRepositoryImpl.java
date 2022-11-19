package ru.zzemlyanaya.takibot.data.repository.impl;

import io.reactivex.rxjava3.core.Completable;
import ru.zzemlyanaya.takibot.data.dao.EntryDao;
import ru.zzemlyanaya.takibot.data.dao.impl.EntryDaoImpl;
import ru.zzemlyanaya.takibot.data.repository.EntryRepository;
import ru.zzemlyanaya.takibot.domain.mapper.DBModelMapper;
import ru.zzemlyanaya.takibot.domain.model.EntryEntity;

/* created by zzemlyanaya on 04/11/2022 */

public class EntryRepositoryImpl implements EntryRepository {

    public static final EntryRepositoryImpl INSTANCE = new EntryRepositoryImpl();

    private final EntryDao entryDao = new EntryDaoImpl();

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
}
