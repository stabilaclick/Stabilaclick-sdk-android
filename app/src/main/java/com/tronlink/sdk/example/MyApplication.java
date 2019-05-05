package com.tronlink.sdk.example;

import android.app.Application;

import com.tronlink.sdk.TronLinkSdk;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TronLinkSdk.getInstance().register(this, "c8925c2c-5375-4e7e-a0a7-4daa29a3e926", "8e1b1ec06054c72d3fbe6b604c399e51");
    }
}
