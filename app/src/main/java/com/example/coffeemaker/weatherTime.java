package com.example.coffeemaker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
//import org.w3c.dom.Document;

import java.io.IOException;

public class weatherTime extends AppCompatActivity {
    TextView textView1, textView2;
    String url = "https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&ssc=tab.nx.all&query=%EB%84%A4%EC%9D%B4%EB%B2%84%EB%82%A0%EC%94%A8&oquery=%EB%84%A4%EC%9D%B4%EB%B2%84%EB%82%A0%EC%94%A8&tqi=iCK7HdpzLiwsskkE6WossssstCw-276301";
    String weather_string, time_string;
    LinearLayout backGroundImg;
    int weather_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* textView1 = (TextView) findViewById(R.id.time);
        textView2 = (TextView) findViewById(R.id.weather);*/

        final Bundle bundle = new Bundle();
        // backGroundImg=(LinearLayout)findViewById(R.id.bg);
        new Thread() {
            @Override
            public void run() {
                Document doc = null;

                try {
                    doc = Jsoup.connect(url).get();

                    // get weather through web crawling
                    Elements content1 = doc.select(".graph_content");
                    weather_string += content1.text();      // weather_string: 크롤링해온 값 저장.
                    String[] split_weather = weather_string.split(" ");      // split_weather: split을 위해 존재.
                    String now_weather = split_weather[1];

                    // send current weather
                    bundle.putString("now_weather", now_weather);
                    Message msg1 = handler.obtainMessage();
                    msg1.setData(bundle);
                    handler.sendMessage(msg1);

                    // get time through web crawling
                    Elements content2 = doc.select(".info");
                    time_string += content2.text();
                    String[] split_time = time_string.split(" ");
                    String now_time = split_time[70].substring(0, 2);

                    // send current time
                    bundle.putString("now_time", now_time);
                    Message msg2 = handler.obtainMessage();
                    msg1.setData(bundle);
                    handler.sendMessage(msg2);


                    // when the current time is night
                    int int_time = Integer.parseInt(now_time);
                    if (int_time >= 19 || int_time <= 7) {
                        if (now_weather == "비" || now_weather == "구름많음" || now_weather == "흐림" || now_weather == "소나기") {
                            weather_index = 2;
                            backGroundImg.setBackgroundResource(R.drawable.hall_night_rain);
                        }
                        else {
                            weather_index = 1;
                            backGroundImg.setBackgroundResource(R.drawable.hall_night);
                        }
                    }
                    else {  // when the current time is day
                        if (now_weather == "비" || now_weather == "구름많음" || now_weather == "흐림" || now_weather == "소나기") {
                            weather_index = 0;
                            backGroundImg.setBackgroundResource(R.drawable.hall_rain);
                        }
                        else {
                            weather_index = 3;
                            backGroundImg.setBackgroundResource(R.drawable.hall_day);
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), StoreHall.class);
                    intent.putExtra("weather_index", weather_index);
                    startActivity(intent);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            textView2.setText(bundle.getString("now_weather"));
            textView1.setText(bundle.getString("now_time"));
        }
    };

}