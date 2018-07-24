package com.gadyez.fishpish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gadyez.fishpish.game.GameCanvasView;

public class GameActivity extends AppCompatActivity {

    private GameCanvasView mGameView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGameView = (GameCanvasView) findViewById(R.id.gameView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mGameView.onStart();
    }

    @Override
    protected void onStop() {

        mGameView.onStop();

        super.onStop();
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
