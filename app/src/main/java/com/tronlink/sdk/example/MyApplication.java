package com.tronlink.sdk.example;

import android.app.Application;

import com.tronlink.sdk.TronLinkSdk;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TronLinkSdk.getInstance().register(this);
    }
}
