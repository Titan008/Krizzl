package com.example.krizzl.Paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.krizzl.ImageHandler;

import java.util.ArrayList;


public class PaintView extends View {

    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private static final float TOUCH_TOLERANCE = 4;
    public static int BRUSH_SIZE = 20;
    public static int DEFAULT_COLOR = Color.BLACK;
    private float mX, mY;
    private Path mPath;
    private Paint mPaint;
    private ArrayList<FingerPath> paths = new ArrayList<>();
    private int currentColor;
    private int backgroundColor = DEFAULT_BG_COLOR;
    private int strokeWidth;
    private boolean small;
    private boolean big;
    public Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private int height;
    private int width;
    final Handler handler = new Handler();


    public PaintView(Context context) {
        this(context, null);
    }


    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);
    }


    public void init(DisplayMetrics metrics) {
        height = metrics.heightPixels;
        width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = DEFAULT_COLOR;
        strokeWidth = BRUSH_SIZE;
    }


    public void normal() {
        small = false;
        big = false;
    }

    public void small() {
        small = true;
        big = false;
    }

    public void big() {
        small = false;
        big = true;
    }


    public void clear() {
        mBitmap.eraseColor(Color.WHITE);
        new ImageHandler().execute(mBitmap);
        paths.clear();

        if (small) {
            small();
        } else if (big) {
            big();
        } else normal();
        invalidate();
    }


    public void blackColor() {
        currentColor = Color.BLACK;
    }

    public void greenColor() {
        currentColor = Color.parseColor("#4CAF50");
    }

    public void blueColor() {
        currentColor = Color.parseColor("#3F51B5");
    }

    public void pinkColor() {
        currentColor = Color.parseColor("#E40AAE");
    }

    public void redColor() {
        currentColor = Color.parseColor("#F44336");
    }

    public void orangeColor() {
        currentColor = Color.parseColor("#FF8C00");
    }

    public void yellowColor() {
        currentColor = Color.parseColor("#FFEB3B");
    }

    public void rubber() {
        currentColor = Color.WHITE;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mCanvas.drawColor(backgroundColor);

        for (FingerPath fp : paths) {
            mPaint.setColor(fp.color);
            mPaint.setStrokeWidth(fp.strokeWidth);
            mPaint.setMaskFilter(null);

            if (fp.small) {
                mPaint.setStrokeWidth(10);
            } else if (fp.big) {
                mPaint.setStrokeWidth(40);
            }
            mCanvas.drawPath(fp.path, mPaint);
        }
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }


    private void touchStart(float x, float y) {
        mPath = new Path();
        FingerPath fp = new FingerPath(currentColor, small, big, strokeWidth, mPath);
        paths.add(fp);
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }


    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }


    private void touchUp() {
        mPath.lineTo(mX, mY);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ImageHandler thread = new ImageHandler();
                        thread.execute(mBitmap);
                    }
                }, 500);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }
        return true;
    }
}
