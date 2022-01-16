package com.techov8.p_droid.FOCUS_TREE.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "tasks")
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "skill_id")
    private int skillId;

    @ColumnInfo(name = "date_created")
    private long dateCreated;

    @ColumnInfo(name = "target_time")
    private long targetTime;

    @ColumnInfo(name = "time_spent")
    private long timeSpent;

    public Task(int skillId, long dateCreated, long targetTime, long timeSpent) {
        this.skillId = skillId;
        this.dateCreated = dateCreated;
        this.targetTime = targetTime;
        this.timeSpent = timeSpent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(long targetTime) {
        this.targetTime = targetTime;
    }

    public long getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }
}
