package ru.zzemlyanaya.takibot.data.repository;

/* created by zzemlyanaya on 04/11/2022 */

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.zzemlyanaya.takibot.domain.model.HabitEntity;

import java.util.List;

public interface HabitRepository {

    Completable createHabitsTable();

    Single<HabitEntity> getHabitById(Long id);
    Single<List<HabitEntity>> getHabitsByUserId(Long id);

    Completable saveHabit(HabitEntity habit);
}
