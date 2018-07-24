package com.gadyez.fishpish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        MusicController.start(MainMenuActivity.this);
    }

    public void onSettingsClick(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicController.stop();
    }

    protected void onResume() {
        super.onResume();
        MusicController.resume();
    }
    public void onPlayClick(View view) { startActivity(new Intent(this, GameActivity.class)); }

}
