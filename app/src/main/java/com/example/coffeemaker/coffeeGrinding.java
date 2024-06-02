package com.example.coffeemaker;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class coffeeGrinding extends AppCompatActivity implements CircleView.OnFinishedDrawingListener {

    private CircleView circleView;
    private TextView additionalTextView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_grinding);

        // 새로운 음악 서비스 시작 : MusicServie2 재생
        Intent serviceIntent2 = new Intent(this, MusicService2.class);
        startService(serviceIntent2);

        circleView = findViewById(R.id.circleView);

        // 추가 텍스트를 포함하는 TextView 찾기
        additionalTextView = findViewById(R.id.additionalTextView);

        // 애니메이션 적용
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(additionalTextView, "alpha", 1f, 0f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setRepeatCount(ObjectAnimator.INFINITE);
        alphaAnimation.setRepeatMode(ObjectAnimator.REVERSE);
        alphaAnimation.start();

        // MediaPlayer 초기화
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.grinder); // grinder.mp3 should be in res/raw folder
            mediaPlayer.setLooping(true); // Loop the audio
            Log.d("coffeeGrinding", "MediaPlayer initialized successfully");
        } catch (Exception e) {
            Log.e("coffeeGrinding", "Error initializing MediaPlayer", e);
        }

        circleView.setOnFinishedDrawingListener(this);

        // Set touch event listener
        circleView.setOnTouchEventListener(new CircleView.OnTouchEventListener() {
            @Override
            public void onStartDrawing() {
                startPlaying();
            }

            @Override
            public void onStopDrawing() {
                stopPlaying();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            Log.d("coffeeGrinding", "MediaPlayer released");
        }
    }

    public void onFinishedDrawing() {
        finish();
    }

    public void startPlaying() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            Log.d("coffeeGrinding", "MediaPlayer started");
        }
    }

    public void stopPlaying() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            Log.d("coffeeGrinding", "MediaPlayer paused");
        }
    }
}
