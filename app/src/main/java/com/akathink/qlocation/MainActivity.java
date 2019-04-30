package com.akathink.qlocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.akathink.qlocation.utils.WifiManagerHelper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.photo_location_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
                startActivity(intent);
            }

        });

        findViewById(R.id.foreground_location_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForegroundActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.background_location_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BackgroundService.class);
                startService(intent);

                finish();
            }
        });


        final WifiManagerHelper wifiManagerHelper = new WifiManagerHelper(this);

        findViewById(R.id.open_wifi_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "打开WiFi-->> " + wifiManagerHelper.setWifiOpen(), Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.close_wifi_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "关闭WiFi-->> " + wifiManagerHelper.setWifiClose(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
