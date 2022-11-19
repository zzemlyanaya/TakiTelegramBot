package ru.zzemlyanaya.takibot.data.repository;

/* created by zzemlyanaya on 04/11/2022 */

import io.reactivex.rxjava3.core.Completable;
import ru.zzemlyanaya.takibot.domain.model.EntryEntity;

public interface EntryRepository {

    Completable createEntriesTable();

    Completable saveEntry(EntryEntity entry);

    Completable updateEntry(EntryEntity entry);
}
