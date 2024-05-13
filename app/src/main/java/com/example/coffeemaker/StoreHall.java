package com.example.coffeemaker;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class StoreHall extends AppCompatActivity {

    ImageView clientImg;//손님 이미지
    TextView clientOrder;//손님 주문 대사
    Button moveKitchen, reStart;//눌렀을 때 kitchen.class, StoreHall2.class로 가는 버튼


    int getIntent=0;//Kitchen에서 Intent 받았는지 확인용 변수

    int order=-1;//무작위로 생성되는 손님의 주문
    int time;//무작위로 생성되는 손님의 방문까지 걸리는 시간

    int past_order;//손님이 주문했던 내용

    Boolean waterOn, milkOn, coffeeOn, iceOn, vanillaOn, lemonOn;//제조음료의 재료 포함 여부

    Intent firstIntent, secondIntent, thirdIntent;//firstIntent는 kitchen에서 데이터 받아오는 인텐트, secondIntent는 kitchen으로 데이터 보내고 화면 전환하는 인텐트, thirdIntent는 StoreHall2로 화면 전환하는 인텐트

    int beverage_completion;//제조음료 완성도

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_store_hall);

        clientImg=(ImageView)findViewById(R.id.client);
        clientOrder=(TextView)findViewById(R.id.Order);
        moveKitchen=(Button)findViewById(R.id.Make);
        reStart=(Button)findViewById(R.id.getNewCliet);


        Intent firstIntent = getIntent();
        getIntent = firstIntent.getIntExtra("intent_index", 0);
        past_order = firstIntent.getIntExtra("order_index", -1);

        if(getIntent==1) {
            if(past_order==0) clientOrder.setText(R.string.order0);
            else if(past_order==1) clientOrder.setText(R.string.order1);
            else if(past_order==2) clientOrder.setText(R.string.order2);
            else if(past_order==3) clientOrder.setText(R.string.order3);
            else if(past_order==4) clientOrder.setText(R.string.order4);
            else if(past_order==5) clientOrder.setText(R.string.order5);
            else if(past_order==6) clientOrder.setText(R.string.order6);
            else if(past_order==7) clientOrder.setText(R.string.order7);
            else if(past_order==8) clientOrder.setText(R.string.order8);
            else if(past_order==9) clientOrder.setText(R.string.order9);
            showClient(past_order, 0, getIntent, clientImg, clientOrder, moveKitchen);

            waterOn = firstIntent.getBooleanExtra("is_waterOn", false);
            milkOn = firstIntent.getBooleanExtra("is_milkOn", false);
            coffeeOn = firstIntent.getBooleanExtra("is_coffeeOn", false);
            iceOn = firstIntent.getBooleanExtra("is_iceOn", false);
            vanillaOn = firstIntent.getBooleanExtra("is_vanillaOn", false);
            lemonOn = firstIntent.getBooleanExtra("is_lemonOn", false);


            boolean answer_ice = true;
            boolean answer_water = true;
            boolean answer_coffee = true;
            boolean answer_milk = true;
            boolean answer_vanilla = true;
            boolean answer_lemon = true;

            // 주문 확인하는 코드.
            if(past_order == 0 ){//아이스 아메리카노
                answer_ice = true;
                answer_water = true;
                answer_coffee = true;
                answer_milk = false;
                answer_vanilla = false;
                answer_lemon = false;
            }
            else if(past_order == 1 ){//핫 아메리카노
                answer_ice = false;
                answer_water = true;
                answer_coffee = true;
                answer_milk = false;
                answer_vanilla = false;
                answer_lemon = false;
            }
            else if (past_order == 2) {//아이스 라떼
                answer_ice = true;
                answer_water = false;
                answer_coffee = true;
                answer_milk = true;
                answer_vanilla = false;
                answer_lemon = false;
            }
            else if (past_order == 3) {//핫 라떼
                answer_ice = false;
                answer_water = false;
                answer_coffee = true;
                answer_milk = true;
                answer_vanilla = false;
                answer_lemon = false;
            }
            else if (past_order==4) {//아이스 바닐라라떼
                answer_ice = true;
                answer_water = false;
                answer_coffee = true;
                answer_milk = true;
                answer_vanilla = true;
                answer_lemon = false;
            }
            else if (past_order==5) {//핫 바닐라라떼
                answer_ice = false;
                answer_water = false;
                answer_coffee = true;
                answer_milk = true;
                answer_vanilla = true;
                answer_lemon = false;
            }
            else if (past_order==6) {//아이스 바닐라아메리카노
                answer_ice = true;
                answer_water = true;
                answer_coffee = true;
                answer_milk = false;
                answer_vanilla = true;
                answer_lemon = false;
            }
            else if (past_order==7) {//핫 바닐라아메리카노
                answer_ice = false;
                answer_water = true;
                answer_coffee = true;
                answer_milk = false;
                answer_vanilla = true;
                answer_lemon = false;
            }
            else if (past_order==8) {//아이스 레몬차
                answer_ice = true;
                answer_water = true;
                answer_coffee = false;
                answer_milk = false;
                answer_vanilla = false;
                answer_lemon = true;
            }
            else if (past_order==9) {//핫 레몬차
                answer_ice = false;
                answer_water = true;
                answer_coffee = false;
                answer_milk = false;
                answer_vanilla = false;
                answer_lemon = true;
            }

            // 비교하는 코드.
            beverage_completion = 6;
            if (answer_ice != iceOn) beverage_completion--;
            if (answer_water != waterOn) beverage_completion--;
            if (answer_coffee != coffeeOn) beverage_completion--;
            if (answer_milk != milkOn) beverage_completion--;
            if (answer_vanilla != vanillaOn) beverage_completion--;
            if (answer_lemon != lemonOn) beverage_completion--;

            if(beverage_completion==6) clientOrder.setText(R.string.completeMsg100);
            else if(beverage_completion==5) clientOrder.setText(R.string.completeMsg85);
            else if(beverage_completion==4) clientOrder.setText(R.string.completeMsg70);
            else if(beverage_completion==3) clientOrder.setText(R.string.completeMsg55);
            else if(beverage_completion==2) clientOrder.setText(R.string.completeMsg40);
            else if(beverage_completion==1) clientOrder.setText(R.string.completeMsg25);
            else if(beverage_completion==0) clientOrder.setText(R.string.completeMsg10);
            else clientOrder.setText(R.string.completeMsg0);

            reStart.setVisibility(View.VISIBLE);
            reStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent thirdIntent = new Intent(getApplicationContext(), StoreHall.class);
                    startActivity(thirdIntent);
                    finish();

                }
            });
        }

        if(getIntent!=1) {
            Random random = new Random();
            time = random.nextInt(8000);
            if(getIntent==1)
                time=0;

            order = random.nextInt(10);
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

            showClient(order, time, getIntent, clientImg, clientOrder, moveKitchen);
        }

        moveKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondIntent = new Intent(getApplicationContext(), StoreKitchen.class);
                secondIntent.putExtra("order_idx", order);
                startActivity(secondIntent);
            }
        });

    }

    private void showClient(Integer order, Integer time, Integer getIntent, ImageView clientImg, TextView clientOrder, Button moveKitchen) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Random random=new Random();
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
}