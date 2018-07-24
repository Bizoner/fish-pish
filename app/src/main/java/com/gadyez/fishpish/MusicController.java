package com.gadyez.fishpish;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicController {
    public static MediaPlayer ring;
    public static void start(Context context) {
        ring = MediaPlayer.create(context,R.raw.backmusic);
//        ring.start();
    }
    public static void stop() {
        ring.stop();
    }
    public static void setVolume(float vol) {
        ring.setVolume(vol,vol);
    }

}
