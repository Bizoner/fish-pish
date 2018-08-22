package com.gadyez.fishpish.leader;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface LeaderDao {
    @Query("SELECT * FROM LeaderEntity ORDER BY score ASC")
    LiveData<List<LeaderEntity>> findAll();

    @Insert
    void insert(LeaderEntity leader);
}