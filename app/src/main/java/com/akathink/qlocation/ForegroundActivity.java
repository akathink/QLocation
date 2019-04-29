package com.akathink.qlocation;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.akathink.qlocation.utils.LocationTool;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

import static android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class ForegroundActivity extends AppCompatActivity {

    private static final String TAG = "ForegroundActivity";
    private TextView mShowTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground);

        mShowTv = findViewById(R.id.show_txt_tv);

        findViewById(R.id.not_request_location_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });


        findViewById(R.id.only_before_q_location_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissionFirst(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION);
            }
        });

        findViewById(R.id.only_after_q_location_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissionFirst(ACCESS_BACKGROUND_LOCATION);
            }
        });

        findViewById(R.id.all_sync_location_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissionFirst(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_BACKGROUND_LOCATION);
            }
        });

        findViewById(R.id.foreground_service_location_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForegroundActivity.this, ForegroundService.class);
                startService(intent);
                finish();
            }
        });
    }

    private void requestPermissionFirst(String... accessBackgroundLocation) {
        AndPermission.with(ForegroundActivity.this)
                .runtime()
                .permission(accessBackgroundLocation)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        getLocation();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(ForegroundActivity.this, "权限拒绝", Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
    }

    private void getLocation() {
        if (LocationTool.isLocationEnabled(ForegroundActivity.this)) {
            Log.d(TAG, "定位可用");

            if (LocationTool.hasForegroundLocationPermission(ForegroundActivity.this)) {
                registerLocationListener();

                Location location = LocationTool.getLocation(ForegroundActivity.this);
                if (location == null) {
                    Log.d(TAG, "地理位置获取失败");
                } else {
                    showLocation(location);
                }
            } else {
                Toast.makeText(ForegroundActivity.this, "没有定位权限", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.d(TAG, "定位不可用");
            LocationTool.openGpsSettings(ForegroundActivity.this);
        }
    }

    private void registerLocationListener() {
        LocationTool.registerLocation(ForegroundActivity.this, 1, 1, new LocationTool.OnLocationChangeListener() {
            @Override
            public void getLastKnownLocation(Location location) {

            }

            @Override
            public void onLocationChanged(Location location) {
                showLocation(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        });
    }

    private void showLocation(Location location) {
        mShowTv.setText("当前位置：\n 经度-->" + location.getLatitude() + "\n 维度-->" + location.getLongitude());
    }
}
