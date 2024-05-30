package com.example.coffeemaker;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class StoreKitchen extends AppCompatActivity {
    //complete 버튼
    Button btnDone;
    //재료 토글버튼
    ImageButton water, milk, ice, coffee, vanilla, lemon, matcha, strawberry;
    //firstIntent는 받아오는 인텐트, secondIntent는 보내는 인텐트
    Intent firstIntent, secondIntent;
    int order, client_num, weather_num;

    // boolean data for check order completion with result
    boolean waterOn, milkOn, iceOn, coffeeOn, vanillaOn, lemonOn, matchaOn, strawberryOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_store_kitchen);

        Context context=getApplicationContext();
        firstIntent=getIntent();
        order = firstIntent.getIntExtra("order_idx", -1);
        client_num = firstIntent.getIntExtra("client_idx", -1);
        weather_num = firstIntent.getIntExtra("weather_index", -3);

        btnDone=(Button)findViewById(R.id.done_button);
        water=(ImageButton)findViewById(R.id.water_toggle);
        milk=(ImageButton)findViewById(R.id.milk_toggle);
        ice=(ImageButton)findViewById(R.id.ice_toggle);
        coffee=(ImageButton)findViewById(R.id.coffee_toggle);
        vanilla=(ImageButton)findViewById(R.id.vanilla_toggle);
        lemon=(ImageButton)findViewById(R.id.lemon_toggle);
        matcha=(ImageButton)findViewById(R.id.matcha_toggle);
        strawberry=(ImageButton)findViewById(R.id.strawberry_toggle);

        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waterOn = !waterOn;
                ViewGroup.LayoutParams params = water.getLayoutParams();
                if (waterOn) {
                    params.width = 300;  // 원하는 너비 (픽셀 단위)
                    params.height = 300; // 원하는 높이 (픽셀 단위)
                } else {
                    params.width = 200;  // 원래 너비 (픽셀 단위)
                    params.height = 200; // 원래 높이 (픽셀 단위)
                }
                water.setLayoutParams(params);
            }
        });
        milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {milkOn = !milkOn;
                ViewGroup.LayoutParams params = milk.getLayoutParams();
                if (milkOn) {
                    params.width = 300;  // 원하는 너비 (픽셀 단위)
                    params.height = 300; // 원하는 높이 (픽셀 단위)
                } else {
                    params.width = 200;  // 원래 너비 (픽셀 단위)
                    params.height = 200; // 원래 높이 (픽셀 단위)
                }
                milk.setLayoutParams(params);}
        });
        ice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IceBreaking.class);
                startActivity(intent);

                iceOn = !iceOn;
                ViewGroup.LayoutParams params = ice.getLayoutParams();
                if (iceOn) {
                    params.width = 300;  // 원하는 너비 (픽셀 단위)
                    params.height = 300; // 원하는 높이 (픽셀 단위)
                } else {
                    params.width = 200;  // 원래 너비 (픽셀 단위)
                    params.height = 200; // 원래 높이 (픽셀 단위)
                }
                ice.setLayoutParams(params);}
        });
        coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {coffeeOn = !coffeeOn;
                ViewGroup.LayoutParams params = coffee.getLayoutParams();
                if (coffeeOn) {
                    params.width = 300;  // 원하는 너비 (픽셀 단위)
                    params.height = 300; // 원하는 높이 (픽셀 단위)
                } else {
                    params.width = 200;  // 원래 너비 (픽셀 단위)
                    params.height = 200; // 원래 높이 (픽셀 단위)
                }
                coffee.setLayoutParams(params);}
        });
        vanilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {vanillaOn = !vanillaOn;
                ViewGroup.LayoutParams params = vanilla.getLayoutParams();
                if (vanillaOn) {
                    params.width = 300;  // 원하는 너비 (픽셀 단위)
                    params.height = 300; // 원하는 높이 (픽셀 단위)
                } else {
                    params.width = 200;  // 원래 너비 (픽셀 단위)
                    params.height = 200; // 원래 높이 (픽셀 단위)
                }
                vanilla.setLayoutParams(params);}
        });
        lemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {lemonOn = !lemonOn;
                ViewGroup.LayoutParams params = lemon.getLayoutParams();
                if (lemonOn) {
                    params.width = 300;  // 원하는 너비 (픽셀 단위)
                    params.height = 300; // 원하는 높이 (픽셀 단위)
                } else {
                    params.width = 200;  // 원래 너비 (픽셀 단위)
                    params.height = 200; // 원래 높이 (픽셀 단위)
                }
                lemon.setLayoutParams(params);}
        });
        matcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {matchaOn = !matchaOn;
                ViewGroup.LayoutParams params = matcha.getLayoutParams();
                if (matchaOn) {
                    params.width = 300;  // 원하는 너비 (픽셀 단위)
                    params.height = 300; // 원하는 높이 (픽셀 단위)
                } else {
                    params.width = 200;  // 원래 너비 (픽셀 단위)
                    params.height = 200; // 원래 높이 (픽셀 단위)
                }
                matcha.setLayoutParams(params);}
        });
        strawberry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {strawberryOn = !strawberryOn;
                ViewGroup.LayoutParams params = strawberry.getLayoutParams();
                if (strawberryOn) {
                    params.width = 300;  // 원하는 너비 (픽셀 단위)
                    params.height = 300; // 원하는 높이 (픽셀 단위)
                } else {
                    params.width = 200;  // 원래 너비 (픽셀 단위)
                    params.height = 200; // 원래 높이 (픽셀 단위)
                }
                strawberry.setLayoutParams(params);}
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*waterOn=water.isChecked();
                milkOn= milk.isChecked();
                iceOn=ice.isChecked();
                coffeeOn=coffee.isChecked();
                vanillaOn=vanilla.isChecked();
                lemonOn=lemon.isChecked();*/

                secondIntent = new Intent(context, StoreHall.class);
                secondIntent.putExtra("is_waterOn", waterOn);
                secondIntent.putExtra("is_coffeeOn", coffeeOn);
                secondIntent.putExtra("is_iceOn", iceOn);
                secondIntent.putExtra("is_milkOn", milkOn);
                secondIntent.putExtra("is_vanillaOn", vanillaOn);
                secondIntent.putExtra("is_lemonOn", lemonOn);
                secondIntent.putExtra("is_matchaOn", matchaOn);
                secondIntent.putExtra("is_strawberryOn", strawberryOn);
                secondIntent.putExtra("intent_index", 1);
                secondIntent.putExtra("order_index", order);
                secondIntent.putExtra("weather_index", weather_num);
                startActivity(secondIntent);
                finish();
            }
        });
    }

}