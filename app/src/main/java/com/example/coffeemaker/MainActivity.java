package com.example.coffeemaker;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.kakao.sdk.auth.AuthApiClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.model.KakaoSdkError;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "KakaoLogin";
    Button startBtn, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        startBtn=(Button)findViewById(R.id.buttonStart);
        loginButton=(Button)findViewById(R.id.login);

        if (AuthApiClient.Companion.getInstance().hasToken()) {
            UserApiClient.Companion.getInstance().accessTokenInfo((accessTokenInfo, error) -> {
                if (error != null) {
                    if (error instanceof KakaoSdkError && ((KakaoSdkError) error).isInvalidTokenError()) {
                        // 로그인 필요
                        // 카카오톡이 설치되어 있는지 확인하는 메서드 , 카카오에서 제공함. 콜백 객체를 이용합.
                        Function2<OAuthToken,Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
                            @Override
                            // 콜백 메서드 ,
                            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                                Log.e(TAG,"CallBack Method");
                                //oAuthToken != null 이라면 로그인 성공
                                if(oAuthToken!=null){
                                    // 토큰이 전달된다면 로그인이 성공한 것이고 토큰이 전달되지 않으면 로그인 실패한다.
                                    updateKakaoLoginUi();

                                }else {
                                    //로그인 실패
                                    Log.e(TAG, "invoke: login fail" );
                                }

                                return null;
                            }
                        };
                    } else {
                        // 기타 에러
                        // 카카오톡이 설치되어 있는지 확인하는 메서드 , 카카오에서 제공함. 콜백 객체를 이용합.
                        Function2<OAuthToken,Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
                            @Override
                            // 콜백 메서드 ,
                            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                                Log.e(TAG,"CallBack Method");
                                //oAuthToken != null 이라면 로그인 성공
                                if(oAuthToken!=null){
                                    // 토큰이 전달된다면 로그인이 성공한 것이고 토큰이 전달되지 않으면 로그인 실패한다.
                                    updateKakaoLoginUi();

                                }else {
                                    //로그인 실패
                                    Log.e(TAG, "invoke: login fail" );
                                }

                                return null;
                            }
                        };
                    }
                } else {
                    // 토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                    updateKakaoLoginUi();
                }
                return Unit.INSTANCE;
            });
        } else {
            // 로그인 필요
            // 카카오톡이 설치되어 있는지 확인하는 메서드 , 카카오에서 제공함. 콜백 객체를 이용합.
            Function2<OAuthToken,Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
                @Override
                // 콜백 메서드 ,
                public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                    Log.e(TAG,"CallBack Method");
                    //oAuthToken != null 이라면 로그인 성공
                    if(oAuthToken!=null){
                        // 토큰이 전달된다면 로그인이 성공한 것이고 토큰이 전달되지 않으면 로그인 실패한다.
                        updateKakaoLoginUi();

                    }else {
                        //로그인 실패
                        Log.e(TAG, "invoke: login fail" );
                    }

                    return null;
                }
            };
        }


        Function2<OAuthToken,Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            // 콜백 메서드 ,
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                Log.e(TAG,"CallBack Method");
                //oAuthToken != null 이라면 로그인 성공
                if(oAuthToken!=null){
                    // 토큰이 전달된다면 로그인이 성공한 것이고 토큰이 전달되지 않으면 로그인 실패한다.
                    updateKakaoLoginUi();

                }else {
                    //로그인 실패
                    Log.e(TAG, "invoke: login fail" );
                }

                return null;
            }
        };

        // 로그인 버튼 클릭 리스너
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 해당 기기에 카카오톡이 설치되어 있는 확인
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)){
                    UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this, callback);
                }else{
                    // 카카오톡이 설치되어 있지 않다면
                    UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, callback);
                }
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), StoreHall.class);
                startActivity(intent);
            }
        });


    }

    private void updateKakaoLoginUi() {

        // 로그인 여부에 따른 UI 설정
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {

                if (user != null) {

                    // 유저의 아이디
                    Log.d(TAG, "invoke: id =" + user.getId());
                    // 유저의 이메일
                    Log.d(TAG, "invoke: email =" + user.getKakaoAccount().getEmail());

                    // 로그인이 되어있으면
                    loginButton.setVisibility(View.GONE);
                    startBtn.setVisibility(View.VISIBLE);

                } else {
                    // 로그인 되어있지 않으면
                    loginButton.setVisibility(View.VISIBLE);
                    startBtn.setVisibility(View.GONE);
                }
                return null;
            }
        });
    }
}
