package ru.zzemlyanaya.takibot.data.dao;

/* created by zzemlyanaya on 23/11/2022 */

import org.apache.ibatis.annotations.Param;
import ru.zzemlyanaya.takibot.data.model.Entry;

import java.util.List;

public interface EntryDao {

    Integer createTable();
    Integer dropTable();

    Integer insert(Entry entry);
    Integer update(Entry entry);

    Entry selectById(Long id);
    List<Entry> selectUserDay(@Param("id") Long id, @Param("date") String date);
    List<Entry> selectUserBetween(
        @Param("id") Long id,
        @Param("start") String startDate,
        @Param("end") String endDate
    );

    Integer deleteById(Long id);
}
