package data.repository.testImpl;

import data.dao.testImpl.HabitDaoTestImpl;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import ru.zzemlyanaya.takibot.data.dao.HabitDao;
import ru.zzemlyanaya.takibot.data.repository.HabitRepository;
import ru.zzemlyanaya.takibot.domain.mapper.DBModelMapper;
import ru.zzemlyanaya.takibot.domain.model.HabitEntity;

import java.util.List;

/* created by zzemlyanaya on 08/11/2022 */

public class HabitRepositoryTestImpl implements HabitRepository {

    public final static HabitRepositoryTestImpl INSTANCE = new HabitRepositoryTestImpl();

    private final HabitDao habitDao = new HabitDaoTestImpl();

    @Override
    public Completable createHabitsTable() {
        return Completable.fromCallable(habitDao::createTable);
    }

    @Override
    public Single<HabitEntity> getHabitById(Long id) {
        return Single.fromCallable(() ->
            DBModelMapper.mapHabitEntity(habitDao.selectById(id))
        );
    }

    @Override
    public Single<List<HabitEntity>> getHabitsByUserId(Long id) {
        return Single.fromCallable(() ->
            DBModelMapper.mapHabits(habitDao.selectByUserId(id))
        );
    }

    @Override
    public Completable saveHabit(HabitEntity habit) {
        return Completable.fromCallable(() -> habitDao.insert(DBModelMapper.mapHabitModel(habit)));
    }
}
