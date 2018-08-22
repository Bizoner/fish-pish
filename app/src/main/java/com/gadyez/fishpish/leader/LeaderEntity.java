package com.gadyez.fishpish.leader;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LeaderEntity {
    @PrimaryKey(autoGenerate = true)
    int uid;
    String nickname;
    int score;

    public void setRecord(String newNick,int newDate) {
        nickname = newNick;
        score = newDate;
    }

    public String getRecordAsString() {
        return this.nickname + '-' + this.score;
    }
}