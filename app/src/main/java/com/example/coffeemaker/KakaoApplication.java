package com.example.coffeemaker;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    private static KakaoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this,"e414ebf3d2a0a5f23d76fd14ea6dab67");
    }
}
