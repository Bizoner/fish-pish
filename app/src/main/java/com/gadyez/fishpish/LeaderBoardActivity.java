package com.gadyez.fishpish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.gadyez.fishpish.leader.LeaderDB;
import com.gadyez.fishpish.leader.LeaderEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LeaderBoardActivity extends AppCompatActivity {

    public List<LeaderEntity> leaderList = new ArrayList<>();
    public RecyclerView recyclerView;
    public LeaderBoardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        mAdapter = new LeaderBoardAdapter(leaderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    public void addUser(String nick, int score) {
        LeaderEntity newLeader = new LeaderEntity();
        newLeader.setRecord(nick,score);
        leaderList.add(newLeader);
        LeaderDB.getInstance(this).addScore(newLeader);
        mAdapter.notifyDataSetChanged();
    }

}