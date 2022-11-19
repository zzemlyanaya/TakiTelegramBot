package data.dao.testImpl;

import ru.zzemlyanaya.takibot.core.dao.BaseDao;
import ru.zzemlyanaya.takibot.data.dao.EntryDao;
import ru.zzemlyanaya.takibot.data.model.Entry;

/* created by zzemlyanaya on 23/11/2022 */

public class EntryDaoTestImpl extends BaseDao<EntryDao> implements EntryDao {


    public EntryDaoTestImpl() {
        super(EntryDao.class, "test");
    }

    @Override
    public Integer createTable() {
        return execute(EntryDao::createTable);
    }

    @Override
    public Integer dropTable() {
        return execute(EntryDao::dropTable);
    }

    @Override
    public Integer insert(Entry entry) {
        return execute(entryDao -> entryDao.insert(entry));
    }

    @Override
    public Entry selectById(Long id) {
        return select(entryDao -> entryDao.selectById(id));
    }

    @Override
    public Integer update(Entry entry) {
        return execute(entryDao -> entryDao.update(entry));
    }

    @Override
    public Integer deleteById(Long id) {
        return execute(entryDao -> entryDao.deleteById(id));
    }
}
