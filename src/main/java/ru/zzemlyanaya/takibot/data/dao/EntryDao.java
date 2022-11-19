package ru.zzemlyanaya.takibot.data.dao;

/* created by zzemlyanaya on 23/11/2022 */

import ru.zzemlyanaya.takibot.data.model.Entry;

public interface EntryDao {

    Integer createTable();
    Integer dropTable();

    Integer insert(Entry entry);
    Entry selectById(Long id);
    Integer update(Entry entry);

    Integer deleteById(Long id);
}
