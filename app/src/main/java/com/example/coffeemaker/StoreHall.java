package com.example.coffeemaker;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class StoreHall extends AppCompatActivity {

    ImageView clientImg;//손님 이미지
    TextView clientOrder;//손님 주문 대사
    Button moveKitchen, reStart;//눌렀을 때 kitchen.class, StoreHall2.class로 가는 버튼
    Beverage userBeverage;//이용자가 생성한 음료
    LinearLayout backGroundImg;


    int getIntent=0;//Kitchen에서 Intent 받았는지 확인용 변수

    int order=-1;//무작위로 생성되는 손님의 주문
    int clientNum =-1;//무작위로 생성되는 손님 이미지
    int time;//무작위로 생성되는 손님의 방문까지 걸리는 시간
    int weather =-1;

    int past_order;//손님이 주문했던 내용
    int past_client;//방문했던 손님 이미지
    int past_weather=-1;
    int beverage_completion;//제조음료 완성도
    Random random = new Random();

    Boolean waterOn, milkOn, coffeeOn, iceOn, vanillaOn, lemonOn;//제조음료의 재료 포함 여부

    Intent firstIntent, secondIntent, thirdIntent, fourthIntent;//firstIntent는 kitchen에서 데이터 받아오는 인텐트, secondIntent는 kitchen으로 데이터 보내고 화면 전환하는 인텐트
    private static final String weatherTAG = "weather";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_store_hall);

        clientImg=(ImageView)findViewById(R.id.client);
        clientOrder=(TextView)findViewById(R.id.Order);
        moveKitchen=(Button)findViewById(R.id.Make);
        reStart=(Button)findViewById(R.id.getNewCliet);
        backGroundImg=(LinearLayout)findViewById(R.id.bg);

        firstIntent = getIntent();
        getIntent = firstIntent.getIntExtra("intent_index", -1);
        past_order = firstIntent.getIntExtra("order_index", -1);
        past_client = firstIntent.getIntExtra("client_idx", -1);

        if(getIntent==1) {

            showWeather(weather);
            //주문했던 손님의 이미지 다시 표시
            showClient(past_order, past_client, 0, getIntent, clientImg, clientOrder, moveKitchen);
            //이용자가 만든 음료 완성도 확인
            beverage_completion=checkComplication(firstIntent);
            //음료 만족도별 손님 리뷰 표시
            showReview(beverage_completion);
            

            reStart.setVisibility(View.VISIBLE);
            reStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thirdIntent = new Intent(getApplicationContext(), StoreHall.class);
                    startActivity(thirdIntent);
                    finish();
                }
            });
        }

        if(getIntent!=1) {
            time = random.nextInt(5000);
            order = random.nextInt(10);
            clientNum = random.nextInt(7);

            showWeather(weather);//날씨별 배경 표시                        ////////////////////////
            showOrder(order);//손님의 주문 표시
            showClient(order, clientNum, time, getIntent, clientImg, clientOrder, moveKitchen);//손님 이미지 표시
        }

        moveKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondIntent = new Intent(getApplicationContext(), StoreKitchen.class);
                secondIntent.putExtra("order_idx", order);
                secondIntent.putExtra("client_idx", clientNum);

                startActivity(secondIntent);
            }
        });
    }
    private class Beverage {
        boolean water;
        boolean milk;
        boolean ice;
        boolean coffee;
        boolean vanilla;
        boolean lemon;

        private Beverage(boolean water, boolean milk, boolean coffee, boolean ice, boolean vanilla, boolean lemon) {
            this.water=water;
            this.milk=milk;
            this.coffee=coffee;
            this.ice=ice;
            this.vanilla=vanilla;
            this.lemon=lemon;
        }
    }
    private int checkComplication(Intent firstIntent) {
        waterOn = firstIntent.getBooleanExtra("is_waterOn", false);
        milkOn = firstIntent.getBooleanExtra("is_milkOn", false);
        coffeeOn = firstIntent.getBooleanExtra("is_coffeeOn", false);
        iceOn = firstIntent.getBooleanExtra("is_iceOn", false);
        vanillaOn = firstIntent.getBooleanExtra("is_vanillaOn", false);
        lemonOn = firstIntent.getBooleanExtra("is_lemonOn", false);

        userBeverage = checkRecipe(past_order);

        // 유저가 만든 음료와 정답 음료 정확도 비교
        beverage_completion = 6;
        if (userBeverage.ice != iceOn) beverage_completion--;
        if (userBeverage.water != waterOn) beverage_completion--;
        if (userBeverage.coffee != coffeeOn) beverage_completion--;
        if (userBeverage.milk != milkOn) beverage_completion--;
        if (userBeverage.vanilla != vanillaOn) beverage_completion--;
        if (userBeverage.lemon != lemonOn) beverage_completion--;

        return beverage_completion;
    }
    private Beverage checkRecipe(Integer order) {

        boolean answer_ice = true;
        boolean answer_water = true;
        boolean answer_coffee = true;
        boolean answer_milk = true;
        boolean answer_vanilla = true;
        boolean answer_lemon = true;

        // 주문 확인하는 코드.
        if(order == 0 ){//아이스 아메리카노
            answer_ice = true;
            answer_water = true;
            answer_coffee = true;
            answer_milk = false;
            answer_vanilla = false;
            answer_lemon = false;
        }
        else if(order == 1 ){//핫 아메리카노
            answer_ice = false;
            answer_water = true;
            answer_coffee = true;
            answer_milk = false;
            answer_vanilla = false;
            answer_lemon = false;
        }
        else if (order == 2) {//아이스 라떼
            answer_ice = true;
            answer_water = false;
            answer_coffee = true;
            answer_milk = true;
            answer_vanilla = false;
            answer_lemon = false;
        }
        else if (order == 3) {//핫 라떼
            answer_ice = false;
            answer_water = false;
            answer_coffee = true;
            answer_milk = true;
            answer_vanilla = false;
            answer_lemon = false;
        }
        else if (order==4) {//아이스 바닐라라떼
            answer_ice = true;
            answer_water = false;
            answer_coffee = true;
            answer_milk = true;
            answer_vanilla = true;
            answer_lemon = false;
        }
        else if (order==5) {//핫 바닐라라떼
            answer_ice = false;
            answer_water = false;
            answer_coffee = true;
            answer_milk = true;
            answer_vanilla = true;
            answer_lemon = false;
        }
        else if (order==6) {//아이스 바닐라아메리카노
            answer_ice = true;
            answer_water = true;
            answer_coffee = true;
            answer_milk = false;
            answer_vanilla = true;
            answer_lemon = false;
        }
        else if (order==7) {//핫 바닐라아메리카노
            answer_ice = false;
            answer_water = true;
            answer_coffee = true;
            answer_milk = false;
            answer_vanilla = true;
            answer_lemon = false;
        }
        else if (order==8) {//아이스 레몬차
            answer_ice = true;
            answer_water = true;
            answer_coffee = false;
            answer_milk = false;
            answer_vanilla = false;
            answer_lemon = true;
        }
        else if (order==9) {//핫 레몬차
            answer_ice = false;
            answer_water = true;
            answer_coffee = false;
            answer_milk = false;
            answer_vanilla = false;
            answer_lemon = true;
        }

        Beverage beverage = new Beverage(answer_water, answer_milk, answer_coffee, answer_ice, answer_vanilla, answer_lemon);

        return beverage;
    }
    private void showReview(Integer beverage_completion) {

        if(beverage_completion==6) clientOrder.setText(R.string.completeMsg100);
        else if(beverage_completion==5) clientOrder.setText(R.string.completeMsg85);
        else if(beverage_completion==4) clientOrder.setText(R.string.completeMsg70);
        else if(beverage_completion==3) clientOrder.setText(R.string.completeMsg55);
        else if(beverage_completion==2) clientOrder.setText(R.string.completeMsg40);
        else if(beverage_completion==1) clientOrder.setText(R.string.completeMsg25);
        else if(beverage_completion==0) clientOrder.setText(R.string.completeMsg10);
        else clientOrder.setText(R.string.completeMsg0);

    }
    private void showOrder(Integer order) {
        if (order==0) clientOrder.setText(R.string.order0);
        else if (order == 1) clientOrder.setText(R.string.order1);
        else if (order == 2) clientOrder.setText(R.string.order2);
        else if (order == 3) clientOrder.setText(R.string.order3);
        else if (order==4) clientOrder.setText(R.string.order4);
        else if (order==5) clientOrder.setText(R.string.order5);
        else if (order==6) clientOrder.setText(R.string.order6);
        else if (order==7) clientOrder.setText(R.string.order7);
        else if (order==8) clientOrder.setText(R.string.order8);
        else if (order==9) clientOrder.setText(R.string.order9);
    }
    private void showClient(Integer order, Integer clientNum, Integer time, Integer getIntent, ImageView clientImg, TextView clientOrder, Button moveKitchen) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Random random=new Random();

                if (clientNum==1) clientImg.setImageResource(R.drawable.client1);
                else if (clientNum==2) clientImg.setImageResource(R.drawable.client2);
                else if (clientNum==3) clientImg.setImageResource(R.drawable.client3);
                else if (clientNum==4) clientImg.setImageResource(R.drawable.client4);
                else if (clientNum==5) clientImg.setImageResource(R.drawable.client5);
                else if (clientNum==6) clientImg.setImageResource(R.drawable.client6);
                else if (clientNum==7) clientImg.setImageResource(R.drawable.client7);
                else clientImg.setImageResource(R.drawable.client);

                clientImg.setVisibility(View.VISIBLE);
                clientOrder.setVisibility(View.VISIBLE);
            }
        }, time);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getIntent!=1) moveKitchen.setVisibility(View.VISIBLE);
            }
        }, time+1000);

    }
    private void showWeather(Integer weather) {

        fourthIntent = getIntent();
        weather = fourthIntent.getIntExtra("weather_index", -1);

        if(weather==0) backGroundImg.setBackgroundResource(R.drawable.hall_rain);
        else if(weather==1) backGroundImg.setBackgroundResource(R.drawable.hall_day);
        else if(weather==2) backGroundImg.setBackgroundResource(R.drawable.hall_night_rain);
        else backGroundImg.setBackgroundResource(R.drawable.hall_night);

        Log.d("weather", "weather: " + weather);
    }


}