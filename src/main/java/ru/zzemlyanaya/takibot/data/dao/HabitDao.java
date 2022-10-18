package ru.zzemlyanaya.takibot.data.dao;

/* created by zzemlyanaya on 04/11/2022 */

import ru.zzemlyanaya.takibot.data.model.Habit;

import java.util.List;

public interface HabitDao {

    Integer createTable();
    Integer dropTable();

    Habit selectById(Long id);
    List<Habit> selectByUserId(Long id);
    Integer deleteById(Long id);

    Integer insert(Habit habit);
}
