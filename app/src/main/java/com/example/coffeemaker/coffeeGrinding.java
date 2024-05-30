package com.example.coffeemaker;

import android.app.Activity;
import android.os.Bundle;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class coffeeGrinding extends AppCompatActivity implements CircleView.OnFinishedDrawingListener {

    private CircleView circleView;
    TextView additionalTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_grinding);
        circleView = findViewById(R.id.circleView);

        // 추가 텍스트를 포함하는 TextView 찾기
        additionalTextView = findViewById(R.id.additionalTextView);

        // 애니메이션 적용
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(additionalTextView, "alpha", 1f, 0f);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setRepeatCount(ObjectAnimator.INFINITE);
        alphaAnimation.setRepeatMode(ObjectAnimator.REVERSE);
        alphaAnimation.start();

        circleView.setOnFinishedDrawingListener(this);
    }

    public void onFinishedDrawing() {
        finish();
    }
}