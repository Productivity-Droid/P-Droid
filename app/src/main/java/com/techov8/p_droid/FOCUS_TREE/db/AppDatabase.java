package com.techov8.p_droid.FOCUS_TREE.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.techov8.p_droid.FOCUS_TREE.db.dao.SkillDao;
import com.techov8.p_droid.FOCUS_TREE.db.dao.TaskDao;
import com.techov8.p_droid.FOCUS_TREE.db.entity.Skill;
import com.techov8.p_droid.FOCUS_TREE.db.entity.Task;




@Database(entities = {Skill.class, Task.class}, version = 1)
public abstract class  AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
    public abstract SkillDao skillDao();
}
