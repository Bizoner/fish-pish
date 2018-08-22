package com.gadyez.fishpish.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

public class Fish {

    private final long mId;
    private final int mImageIndex;
    private final Rect rectangle;
    private final double factor;
    private Bitmap fishBitmap;
    private final int mCanvasWidth;

    //    private int mX;
//    private final int mY;
    private boolean mIsHooked;

    static Fish generateFish(Context context, int imageIndex, int canvasWidth, int canvasHeight) {
        return new Fish(context, imageIndex, canvasWidth, canvasHeight);
    }

    private Fish(Context context, int imageIndex, int canvasWidth, int canvasHeight) {

        mCanvasWidth = canvasWidth;

        final Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("fish" + imageIndex, "drawable", context.getPackageName());
        fishBitmap = ((BitmapDrawable) resources.getDrawable(resourceId)).getBitmap();

        factor = 1 + Math.random() + Math.random();

        final double w = fishBitmap.getWidth() * factor;
        final double h = fishBitmap.getHeight() * factor;
        rectangle = new Rect(0, 0, (int) w, (int) h);


        mImageIndex = imageIndex;
        mId = System.currentTimeMillis();


        rectangle.offset(canvasWidth + (int) w, (int) ((double) canvasHeight * Math.random()));

//        mX = rectangle.left;
//        mY = rectangle.top;


    }

    public void onCalculateFrame(int rodHeight) {


        if (mIsHooked) {

            if (rectangle.top == 0) {
                // fished reached top of screen
                rectangle.offsetTo(0 - rectangle.width(), 0);
            } else {
                rectangle.offsetTo(rectangle.left, rodHeight - 80);
//                rectangle.offset();

            }

        } else {
            rectangle.offset(-8, 0);
        }


    }

    public Canvas onDraw(Canvas canvas) {

//        canvas.drawBitmap(fishBitmap, (float) mX, (float) mY, new Paint());

        if (fishBitmap != null && !fishBitmap.isRecycled()) {
            canvas.drawBitmap(fishBitmap, null, rectangle, new Paint());
        }


        return canvas;
    }

    public boolean isDone() {
        return (rectangle.left < 0 - rectangle.width()) || (rectangle.top <= 0);
    }

    public void recycle() {
//        fishBitmap.recycle();
//        fishBitmap = null;
    }

    public boolean isTouching(GameCanvasView.FishingRod mFishingRod) {
        return mFishingRod.isContained(rectangle);
    }

    public void setHooked(boolean b) {
        mIsHooked = true;
    }

    public double getScore() {
        return factor*10;
    }

    public boolean isHooked() {
        return mIsHooked;
    }
}
