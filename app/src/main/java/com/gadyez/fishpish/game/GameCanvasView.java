package com.gadyez.fishpish.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gadyez.fishpish.PixelCalc;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameCanvasView extends View {
    public static final int FPS_PERIOD = 1000 / 60;
    private int mCanvasWidth;
    private int mCanvasHeight;
    private boolean mSizesCalculated;

    private static Random mRandom = new Random();

    private FishingRod mFishingRod;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean mIsTouching;
    private Paint mRodPaint;
    private Direction mCurrentDirection;
    private boolean mDirectionLocked;
    private int mRodPace;
    private float mTouchDownX;
    private float mRodX;
    private Paint mScorePaint;


//    private float mWaterHeight;

    private final ArrayList<Fish> mFishList = new ArrayList<>();
    private TimerTask mFishTask;
    private double mCurrentScore;


    public GameCanvasView(Context context) {
        super(context);

        init();
    }

    public GameCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public GameCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        // nothing yet


        mRodPace = (int) PixelCalc.convertDpToPixel(2, getContext());

        mRodPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRodPaint.setColor(Color.BLACK);
        mRodPaint.setStrokeWidth(6);

        mScorePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScorePaint.setColor(Color.BLACK);
        mScorePaint.setTextSize(50);


        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        mTouchDownX = event.getX();

                        mIsTouching = true;

                        return true;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:

                        mIsTouching = false;

                        return true;
                }

                return false;
            }
        });

    }

    public void onStart() {

        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {

                onCalculateRodAndFish();

            }
        };

        mFishTask = new TimerTask() {

            @Override
            public void run() {


                if (mSizesCalculated) {
                    synchronized (mFishList) {
                        mFishList.add(Fish.generateFish(getContext(), mRandom.nextInt(8) + 1, mCanvasWidth, mCanvasHeight));
                    }
                }

            }
        };

        // recalculate every 16 ms for 60 fps animations
        mTimer.schedule(mTimerTask, 0, FPS_PERIOD);
        mTimer.schedule(mFishTask, 1000, 1500);


    }


    public void onStop() {
        mTimerTask.cancel();
        mTimer.cancel();
    }

    private void onCalculateRodAndFish() {

        if (mSizesCalculated) {

            mFishingRod.onCalculateFrame(mIsTouching, mTouchDownX);
            Fish fish;
            for (int i = 0; i < mFishList.size(); i++) {

                fish = mFishList.get(i);

                fish.onCalculateFrame(mFishingRod.mCurrentLength);

                synchronized (mFishList) {

                    boolean removed = false;

                    if (fish.isDone()) {
                        fish.recycle();
                        if (fish.isHooked()) {
                            mCurrentScore += fish.getScore();
                            Log.v("score",String.valueOf(mCurrentScore));
                            fish.setHooked(false);

                        }

                        mFishList.remove(i);

                        removed = true;

                    }

                    if (!removed) {

                        if (fish.isTouching(mFishingRod)) {

                            fish.setHooked(true);

                        }
                    }

                }
            }
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCanvasWidth = w;
        mCanvasHeight = h;

        if (mCanvasWidth > 0 && mCanvasHeight > 0) {

//            mWaterHeight = 0.7f * (float) mCanvasHeight;

            mFishingRod = new FishingRod(mCanvasHeight);

            mSizesCalculated = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!mSizesCalculated) return;


        // draw background
//        canvas.drawColor(Color.BLUE);

        // draw fishing rod
        canvas = mFishingRod.onDraw(canvas);

        canvas.drawText(String.valueOf(mCurrentScore),40,40,mScorePaint);


        synchronized (mFishList) {

            for (int i = 0; i < mFishList.size(); i++) {
                canvas = mFishList.get(i).onDraw(canvas);
            }

        }
        invalidate();
    }

    class FishingRod {

        private final int mMaxLength;
        private int mCurrentLength;

        FishingRod(int maxLength) {
            this.mMaxLength = maxLength;
            this.mCurrentLength = 0;

        }


        Canvas onDraw(Canvas canvas) {

            canvas.drawLine(mRodX, 0, mRodX, mCurrentLength, mRodPaint);

            return canvas;
        }

        void onCalculateFrame(boolean isTouching, float touchDownX) {


            // 1 pixel per frame :)

            if (isTouching) {


                // don't go over screen edge
                if (mCurrentLength < mMaxLength) {

                    if (mCurrentLength == 0) {
                        mRodX = touchDownX;
                    }
                    // go down

                    mCurrentDirection = Direction.Down;

                    mCurrentLength = mCurrentLength + (mRodPace * 1);

                }


            } else {

//                if (mDirectionLocked && mCurrentLength <= 0) {
//                    mDirectionLocked = false;
//                }

                // don't go over screen edge
                if (mCurrentLength >= 0) {

                    mCurrentDirection = Direction.Up;

                    mCurrentLength = mCurrentLength + (mRodPace * -1);

                }

//                if (mCurrentLength > 0) {
//                    // once it stars going up, we want to lock the rod
//                    mDirectionLocked = true;
//                }
            }

        }


        public boolean isContained(Rect rectangle) {
            return rectangle.contains((int) mRodX, mCurrentLength);
        }
    }
}
