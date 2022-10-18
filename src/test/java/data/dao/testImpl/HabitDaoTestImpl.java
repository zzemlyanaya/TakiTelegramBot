package data.dao.testImpl;

import ru.zzemlyanaya.takibot.core.dao.BaseDao;
import ru.zzemlyanaya.takibot.data.dao.HabitDao;
import ru.zzemlyanaya.takibot.data.model.Habit;

import java.util.List;

/* created by zzemlyanaya on 08/11/2022 */

public class HabitDaoTestImpl extends BaseDao<HabitDao> implements HabitDao {

    public HabitDaoTestImpl() {
        super(HabitDao.class, "test");
    }

    @Override
    public Integer createTable() {
        return execute(HabitDao::createTable);
    }

    @Override
    public Integer dropTable() {
        return execute(HabitDao::dropTable);
    }

    @Override
    public Habit selectById(Long id) {
        return select(habitDao -> habitDao.selectById(id));
    }

    @Override
    public List<Habit> selectByUserId(Long id) {
        return select(habitDao -> habitDao.selectByUserId(id));
    }

    @Override
    public Integer deleteById(Long id) {
        return execute(habitDao -> habitDao.deleteById(id));
    }

    @Override
    public Integer insert(Habit habit) {
        return execute(habitDao -> habitDao.insert(habit));
    }
}
