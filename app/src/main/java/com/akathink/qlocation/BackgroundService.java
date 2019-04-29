package com.akathink.qlocation;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.akathink.qlocation.utils.LocationTool;

public class BackgroundService extends IntentService {

    private static final String TAG = "BackgroundService";

    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    public void onCreate() {
        Log.i("momocsdn", "onCreate - Thread ID = " + Thread.currentThread().getId());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (true) {

            Location location = LocationTool.getLocation(BackgroundService.this);

            if (location != null) {
                Log.d(TAG, "当前位置：\n 经度-->" + location.getLatitude() + "\n 维度-->" + location.getLongitude());
            } else {
                Log.d(TAG, "没有后台定位权限，获取失败");
            }


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("momocsdn", "onBind - Thread ID = " + Thread.currentThread().getId());
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i("momocsdn", "onDestroy - Thread ID = " + Thread.currentThread().getId());
        super.onDestroy();
    }
}
