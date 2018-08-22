package com.gadyez.fishpish;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.crashlytics.android.Crashlytics;

import java.util.Timer;
import java.util.TimerTask;

import io.fabric.sdk.android.Fabric;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main_menu);
        MusicController.start(MainMenuActivity.this);
    }

    public void onSettingsClick(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
    public void onLeaderBoardClick(View view) {
        startActivity(new Intent(this, LeaderBoardActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicController.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MusicController.resume();
    }
    public void onPlayClick(View view) { startActivity(new Intent(this, GameActivity.class)); }

}
