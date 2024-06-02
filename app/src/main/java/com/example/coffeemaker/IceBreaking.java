package com.example.coffeemaker;
//button -> ice breaking

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class IceBreaking extends AppCompatActivity {

    private ImageView imageView;
    private Button button;
    private TextView touchCountTextView;
    private int touchCount = 0;
    private MediaPlayer mediaPlayer;
    private Random random;
    ImageView hammerImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ice_breaking);

        // 새로운 음악 서비스 시작 : MusicServie2 재생
        Intent serviceIntent2 = new Intent(this, MusicService2.class);
        startService(serviceIntent2);


        imageView = findViewById(R.id.imageView);
        hammerImageView = findViewById(R.id.hammerImageView);
        touchCountTextView = findViewById(R.id.touchCountTextView);
        mediaPlayer = MediaPlayer.create(this, R.raw.hammersound1); // 초기 소리 설정
        random = new Random();

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 이미지 뷰를 터치할 때마다 터치 횟수를 증가시키고 텍스트뷰에 출력
                touchCount++;
                touchCountTextView.setText("Touch Count: " + touchCount);

                if(touchCount==25) finish();
                // count == 5,10,15,20일 때 얼음 사진을 변경하여 녹는 모습 연출
                if (touchCount == 5) {
                    imageView.setImageResource(R.drawable.ice2);
                    playClearSound();
                } else if (touchCount == 10) {
                    imageView.setImageResource(R.drawable.ice3);
                    playClearSound();
                } else if (touchCount == 15) {
                    imageView.setImageResource(R.drawable.ice4);
                    playClearSound();
                } else if (touchCount == 20) {
                    imageView.setImageResource(R.drawable.ice5);
                    playClearSound();
                    //추가 코드 작성 : 새로운 화면 액티비티로 넘어가야 함
                } else {
                    // 그 외에는 랜덤 소리 재생
                    playRandomSound();
                }

                // Hammer 이미지 보여주기 및 애니메이션 적용
                hammerImageView.setVisibility(View.VISIBLE);
                Animation rotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_animation);
                hammerImageView.startAnimation(rotateAnimation);

                return false; // 이벤트를 계속 전달하기 위해 false 반환
            }
        });
    }
    private void playRandomSound() {
        int randomNumber = random.nextInt(2); // 0 또는 1을 랜덤으로 생성
        if (randomNumber == 0) {
            // hammersound1 재생
            mediaPlayer = MediaPlayer.create(this, R.raw.hammersound1);
        } else {
            // hammersound2 재생
            mediaPlayer = MediaPlayer.create(this, R.raw.hammersound3);
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 재생이 끝나면 MediaPlayer 해제
                mp.release();
            }
        });

        mediaPlayer.start(); // 사운드 재생
    }

    private void playClearSound() {
        // clear.mp3 재생
        mediaPlayer = MediaPlayer.create(this, R.raw.clear);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // 재생이 끝나면 MediaPlayer 해제
                mp.release();
            }
        });
        mediaPlayer.start(); // 사운드 재생
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 액티비티가 종료될 때 MediaPlayer 해제
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}