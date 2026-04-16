package com.example.androidportfolio.ui.doodle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingCanvas extends View {

    private Paint paint;
    private Paint brushPaint;
    private Bitmap bitmap;
    private Canvas bitmapCanvas;
    private float lastX;
    private float lastY;
    private int paintColor = 0xFF000000; // Start with black

    public DrawingCanvas(Context context) {
        super(context);
        init();
    }

    public DrawingCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawingCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        brushPaint = new Paint();
        brushPaint.setAntiAlias(true);
        brushPaint.setDither(true);
        brushPaint.setColor(paintColor);
        brushPaint.setStyle(Paint.Style.STROKE);
        brushPaint.setStrokeJoin(Paint.Join.ROUND);
        brushPaint.setStrokeCap(Paint.Cap.ROUND);
        brushPaint.setStrokeWidth(5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(0xFFFFFFFF); // White background
            bitmapCanvas = new Canvas(bitmap);
        }
    }

    public void setDrawColor(int color) {
        paintColor = color;
        brushPaint.setColor(color);
    }

    public void clear() {
        if (bitmap != null) {
            bitmap.eraseColor(0xFFFFFFFF);
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (bitmapCanvas != null) {
                    bitmapCanvas.drawLine(lastX, lastY, x, y, brushPaint);
                }
                lastX = x;
                lastY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
