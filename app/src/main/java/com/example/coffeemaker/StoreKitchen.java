package com.example.coffeemaker;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class StoreKitchen extends AppCompatActivity {
    //complete 버튼
    Button btnDone;
    //재료 토글버튼
    ToggleButton water, milk, ice, coffee, vanilla, lemon;
    //firstIntent는 받아오는 인텐트, secondIntent는 보내는 인텐트
    Intent firstIntent, secondIntent;
    int order, client_num, weather_num;

    // boolean data for check order completion with result
    boolean waterOn, milkOn, iceOn, coffeeOn, vanillaOn, lemonOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_store_kitchen);

        Context context=getApplicationContext();
        firstIntent=getIntent();
        order = firstIntent.getIntExtra("order_idx", -1);
        client_num = firstIntent.getIntExtra("client_idx", -1);
        weather_num = firstIntent.getIntExtra("weather_idx", -1);

        btnDone=(Button)findViewById(R.id.done_button);
        water=(ToggleButton)findViewById(R.id.water_toggle);
        milk=(ToggleButton)findViewById(R.id.milk_toggle);
        ice=(ToggleButton)findViewById(R.id.ice_toggle);
        coffee=(ToggleButton)findViewById(R.id.coffee_toggle);
        vanilla=(ToggleButton)findViewById(R.id.vanilla_toggle);
        lemon=(ToggleButton)findViewById(R.id.lemon_toggle);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                waterOn=water.isChecked();
                milkOn= milk.isChecked();
                iceOn=ice.isChecked();
                coffeeOn=coffee.isChecked();
                vanillaOn=vanilla.isChecked();
                lemonOn=lemon.isChecked();

                secondIntent = new Intent(context, StoreHall.class);
                secondIntent.putExtra("is_waterOn", waterOn);
                secondIntent.putExtra("is_coffeeOn", coffeeOn);
                secondIntent.putExtra("is_iceOn", iceOn);
                secondIntent.putExtra("is_milkOn", milkOn);
                secondIntent.putExtra("is_vanillaOn", vanillaOn);
                secondIntent.putExtra("is_lemonOn", lemonOn);
                secondIntent.putExtra("intent_index", 1);
                secondIntent.putExtra("order_index", order);
                secondIntent.putExtra("client_idx", client_num);
                secondIntent.putExtra("weather_idx", weather_num);
                startActivity(secondIntent);
                finish();
            }
        });
    }

}