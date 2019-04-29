package com.akathink.qlocation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class WifiManagerHelper {
    private WifiManager mWifiManager;

    public WifiManagerHelper(Context context) {
        mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public WifiInfo getWifiInfo() {
        return mWifiManager.getConnectionInfo();
    }


    /**
     * 在Android Q上的应用无法启用或者停用Wi-Fi，以下始终返回false
     * 如果想要开启或停用，只能使用设置面板里面的
     *
     * @return
     */
    public boolean setWifiOpen() {
        return mWifiManager.setWifiEnabled(true);
    }

    public boolean setWifiClose() {
        return mWifiManager.setWifiEnabled(false);
    }
}
