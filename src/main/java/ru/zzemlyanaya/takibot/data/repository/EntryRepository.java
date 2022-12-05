package ru.zzemlyanaya.takibot.data.repository;

/* created by zzemlyanaya on 04/11/2022 */

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.zzemlyanaya.takibot.domain.model.EntryEntity;

import java.time.LocalDate;
import java.util.List;

public interface EntryRepository {

    Completable createEntriesTable();

    Completable saveEntry(EntryEntity entry);

    Completable updateEntry(EntryEntity entry);

    Single<List<EntryEntity>> getTodayEntries(Long userId);
    Single<List<EntryEntity>> getEntriesBetween(Long userId, LocalDate startDate, LocalDate endDate);
}
