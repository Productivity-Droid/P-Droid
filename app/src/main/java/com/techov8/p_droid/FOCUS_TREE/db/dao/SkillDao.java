package com.techov8.p_droid.FOCUS_TREE.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.techov8.p_droid.FOCUS_TREE.db.entity.Skill;

import java.util.List;


@Dao
public interface SkillDao {
    @Query("SELECT * FROM skills")
    List<Skill> getAll();

    @Query("SELECT * FROM skills WHERE id = :id")
    Skill getById(int id);

    @Insert
    void insertAll(Skill... skills);

    @Delete
    void delete(Skill skill);

    @Update
    void update(Skill skill);

}
