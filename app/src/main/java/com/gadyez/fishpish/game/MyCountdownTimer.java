package com.gadyez.fishpish.game;

import android.os.CountDownTimer;

public class MyCountdownTimer {

    long mCurrentMilisLeft;
    long mInterval;
    boolean done = false;
    CountdownTimerWrapper mCountdownTimer;

    class CountdownTimerWrapper extends CountDownTimer {
        public CountdownTimerWrapper(long millisInFuture,long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            done = true;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mCurrentMilisLeft = millisUntilFinished;
        }

    }

    public MyCountdownTimer(long millisInFuture, long countDownInterval) {
        set(millisInFuture,countDownInterval);
    }

    public boolean isFinished() {
        return done;
    }

    public void pause(){
        mCountdownTimer.cancel();
    }

    public void resume(){
        mCountdownTimer = new CountdownTimerWrapper(mCurrentMilisLeft, mInterval);
        mCountdownTimer.start();
    }

    public long getTimeLeft() {
        return mCurrentMilisLeft;
    }

    public void start(){
        mCountdownTimer.start();
    }

    public void set(long millisInFuture, long countDownInterval){
        mInterval = countDownInterval;
        mCurrentMilisLeft = millisInFuture;
        mCountdownTimer = new CountdownTimerWrapper(millisInFuture, countDownInterval);
    }

}