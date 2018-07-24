package com.gadyez.fishpish;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;


public class SettingsActivity extends AppCompatActivity {

    SeekBar simpleSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        simpleSeekBar = findViewById(R.id.backVol);
        simpleSeekBar.setProgress(100);

        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                float current = (float) progress / 100;
                Log.v("curr",""+current);
                MusicController.setVolume(current);
            }

        });

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
}
