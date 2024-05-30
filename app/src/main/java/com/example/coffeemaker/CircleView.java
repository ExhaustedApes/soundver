package com.example.coffeemaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CircleView extends View {
    // 인터페이스 정의
    public interface OnFinishedDrawingListener {
        void onFinishedDrawing();
    }

    private Paint borderPaint;
    private Paint drawPaint;
    private Paint textPaint;
    private int centerX, centerY;
    private int radius;
    private List<Float> pointsX;
    private List<Float> pointsY;
    private boolean isDrawing;
    private int majorPointCount = 12;
    private float majorPointAngle;
    private int pointsPassedCount;
    private boolean isFinished;
    private boolean[] majorPointsPassed;
    private float[][] majorPointsCoordinates;
    private float majorPointRadius = 20; // 주요 포인트 인식 반경
    private OnFinishedDrawingListener listener; // 리스너 변수 추가

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        borderPaint = new Paint();
        borderPaint.setColor(Color.argb(128, 255, 255, 255));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(40);
        borderPaint.setAntiAlias(true);

        drawPaint = new Paint();
        drawPaint.setColor(Color.BLACK);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeWidth(25);
        drawPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);

        pointsX = new ArrayList<>();
        pointsY = new ArrayList<>();
        isDrawing = false;
        isFinished = false;

        majorPointAngle = 360f / majorPointCount;
        majorPointsPassed = new boolean[majorPointCount];
        majorPointsCoordinates = new float[majorPointCount][2];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        radius = Math.min(w, h) / 3;
        calculateMajorPointsCoordinates();
    }

    private void calculateMajorPointsCoordinates() {
        for (int i = 0; i < majorPointCount; i++) {
            float angle = i * majorPointAngle;
            float x = centerX + radius * (float) Math.cos(Math.toRadians(angle));
            float y = centerY + radius * (float) Math.sin(Math.toRadians(angle));
            majorPointsCoordinates[i][0] = x;
            majorPointsCoordinates[i][1] = y;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerX, centerY, radius, borderPaint);
        drawMajorPoints(canvas);
        drawStartPoint(canvas);
        drawLines(canvas);
    }

    private void drawMajorPoints(Canvas canvas) {
        Paint whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setAntiAlias(true);
        for (int i = 0; i < majorPointCount; i++) {
            float x = majorPointsCoordinates[i][0];
            float y = majorPointsCoordinates[i][1];
            canvas.drawCircle(x, y, 10, whitePaint);
        }
    }

    private void drawStartPoint(Canvas canvas) {
        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        if (!pointsX.isEmpty() && !pointsY.isEmpty()) {
            canvas.drawCircle(pointsX.get(0), pointsY.get(0), 20, redPaint);
        }
    }

    private void drawLines(Canvas canvas) {
        if (isDrawing) {
            for (int i = 1; i < pointsX.size(); i++) {
                float x1 = pointsX.get(i - 1);
                float y1 = pointsY.get(i - 1);
                float x2 = pointsX.get(i);
                float y2 = pointsY.get(i);
                canvas.drawLine(x1, y1, x2, y2, drawPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isTouchingBorder(x, y)) {
                    isDrawing = true;
                    pointsX.clear();
                    pointsY.clear();
                    pointsPassedCount = 0;
                    pointsX.add(x);
                    pointsY.add(y);
                    for (int i = 0; i < majorPointCount; i++) {
                        majorPointsPassed[i] = false;
                    }
                    invalidate();
                    return true;
                }
                return false;
            case MotionEvent.ACTION_MOVE:
                if (isDrawing) {
                    pointsX.add(x);
                    pointsY.add(y);
                    checkMajorPointsPassed(x, y);
                    invalidate();
                    return true;
                }
                return false;
            case MotionEvent.ACTION_UP:
                if (isDrawing) {
                    isDrawing = false;
                    if (isFinishedDrawing()) {
                        isFinished = true;
                        Log.d("CircleView", "Drawing finished!");
                        if (listener != null) {
                            listener.onFinishedDrawing(); // 리스너 호출
                        }
                    } else {
                        Log.d("CircleView", "Drawing not finished.");
                    }
                    invalidate();
                    return true;
                }
                return false;
            default:
                return super.onTouchEvent(event);
        }
    }

    private boolean isTouchingBorder(float x, float y) {
        double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
        return Math.abs(distance - radius) <= 40;
    }

    private void checkMajorPointsPassed(float x, float y) {
        for (int i = 0; i < majorPointCount; i++) {
            if (!majorPointsPassed[i]) {
                float pointX = majorPointsCoordinates[i][0];
                float pointY = majorPointsCoordinates[i][1];
                double distance = Math.sqrt(Math.pow(pointX - x, 2) + Math.pow(pointY - y, 2));
                if (distance <= majorPointRadius) {
                    majorPointsPassed[i] = true;
                    pointsPassedCount++;
                }
            }
        }
        Log.d("CircleView", "Points passed count: " + pointsPassedCount);
    }

    private boolean isFinishedDrawing() {
        boolean allPointsPassed = pointsPassedCount >= 10;
        Log.d("CircleView", "All points passed: " + allPointsPassed);
        return allPointsPassed;
    }

    // 리스너를 설정하는 메서드 추가
    public void setOnFinishedDrawingListener(OnFinishedDrawingListener listener) {
        this.listener = listener;
    }
}
