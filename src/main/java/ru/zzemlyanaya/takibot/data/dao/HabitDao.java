package ru.zzemlyanaya.takibot.data.dao;

/* created by zzemlyanaya on 04/11/2022 */

import org.apache.ibatis.annotations.Param;
import ru.zzemlyanaya.takibot.data.model.Habit;

import java.util.List;

public interface HabitDao {

    Integer createTable();
    Integer dropTable();

    Habit selectById(Long id);
    List<Habit> selectByUserId(Long id);
    List<Habit> selectByUserAndDate(@Param("id") Long id, @Param("date") String date);

    Integer setNextDate(@Param("id") Long id, @Param("nextDate") String nextDate);
    Integer deleteById(Long id);
    Integer insert(Habit habit);
}
