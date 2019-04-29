package com.akathink.qlocation;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.annotation.Nullable;
import android.util.Log;

import com.akathink.qlocation.utils.LocationTool;

public class ForegroundService extends IntentService {

    private static final String TAG = "ForegroundService";

    public ForegroundService() {
        super("ForegroundService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        String CHANNEL_ID = "360";
        String CHANNEL_Name = "channel_name";
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_Name, NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        Notification.Builder builder = new Notification.Builder(this, CHANNEL_ID);
        builder.setContentTitle("前台服务");
        builder.setContentText("获取地理位置信息");
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setChannelId(CHANNEL_ID);
        builder.setContentIntent(pi);
        Notification notification = builder.build();
        startForeground(1, notification);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (true) {

            Location location = LocationTool.getLocation(ForegroundService.this);

            if (location != null) {
                Log.d(TAG, "当前位置：\n 经度-->" + location.getLatitude() + "\n 维度-->" + location.getLongitude());
            } else {
                Log.d(TAG, "没有前台定位权限，获取失败");
            }


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
