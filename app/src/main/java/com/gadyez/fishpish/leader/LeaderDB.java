package com.gadyez.fishpish.leader;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import java.util.List;

@Database(entities = {LeaderEntity.class}, version = 1)
public abstract class LeaderDB extends RoomDatabase {
    private static final String TAG = LeaderDB.class.getSimpleName();
    private static LeaderDB INSTANCE;

    public static LeaderDB getInstance(Context context) {
        synchronized (LeaderDB.class) {
            if (INSTANCE == null) {
                // notice getApplicationContext
                // -- it prevents the memory leak that would happen if the activity was passed
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        LeaderDB.class, "ndlog.db")
//                        .addMigrations(MIGRATION_1_2) // placeholder for future db versions
                        .build();
            }
            return INSTANCE;
        }
    }
    public abstract LeaderDao getLeaderDao();
    public LiveData<List<LeaderEntity>> getScores() {
        LiveData<List<LeaderEntity>> leaderEntity = getLeaderDao().findAll();
        return leaderEntity;
    }
    public void addScore(final LeaderEntity leaderEntity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getLeaderDao().insert(leaderEntity);
            }
        }).start();
    }
}