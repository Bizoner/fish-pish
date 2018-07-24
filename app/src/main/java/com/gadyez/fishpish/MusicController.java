package com.gadyez.fishpish;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicController {
    public static MediaPlayer ring;
    public static void start(Context context) {
        ring = MediaPlayer.create(context,R.raw.newbackmusic);
        ring.start();
    }
    public static void stop() {
        ring.pause();
    }
    public static void resume() {
        ring.start();
    }
    public static void setVolume(float vol) {
        ring.setVolume(vol,vol);
    }

}
