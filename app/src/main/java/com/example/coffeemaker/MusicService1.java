package com.example.coffeemaker;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService1 extends Service {
    private static final String TAG = "MusicService1";
    private MediaPlayer mediaPlayer; // MediaPlayer 객체 선언

    @Override
    public void onCreate() { // Service가 생성될 때 호출되는 메서드
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.background); // MediaPlayer 객체를 생성하고 background.mp3 파일을 재생합니다.
        mediaPlayer.setLooping(true); // 무한 반복 설정
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { // startService()를 호출할 때마다 실행되는 메서드
        if (!mediaPlayer.isPlaying()) { // MediaPlayer가 현재 재생 중이 아닌 경우
            mediaPlayer.start(); // MediaPlayer를 시작합니다.
        }
        return START_STICKY; // 시스템이 서비스를 강제로 종료한 후에, 사용자가 서비스를 재시작할 수 있도록 합니다.
    }

    @Override
    public void onDestroy() { // 서비스가 종료될 때 호출되는 메서드
        if (mediaPlayer.isPlaying()) { // MediaPlayer가 현재 재생 중인 경우
            mediaPlayer.stop(); // MediaPlayer를 정지합니다.
        }
        mediaPlayer.release(); // MediaPlayer를 해제하여 자원을 반환합니다.
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) { // 바인딩된 경우 호출되는 메서드
        return null; // 이 서비스는 바인딩을 지원하지 않으므로 null을 반환합니다.
    }
}
