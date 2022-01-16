package com.techov8.p_droid.FOCUS_TREE.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.techov8.p_droid.FOCUS_TREE.db.entity.Task;

import java.util.List;


@Dao
public interface TaskDao {

    @Query("SELECT * FROM tasks WHERE skill_id = :skillId")
    List<Task> getAll(int skillId);

    @Query("SELECT * FROM tasks WHERE date_created BETWEEN :p1 AND :p2")
    List<Task> getAllBetween(Long p1, Long p2);

    @Query("SELECT * FROM tasks WHERE id = :id")
    Task getById(int id);

    @Insert
    void insertAll(Task... tasks);

    @Delete
    void delete(Task task);

    @Update
    void update(Task task);

}
