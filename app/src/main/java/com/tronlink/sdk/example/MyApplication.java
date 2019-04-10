package com.tronlink.sdk.example;

import android.app.Application;

import com.tronlink.sdk.TronLinkSdk;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TronLinkSdk.getInstance().register(this, "kiptvu650vjvnr8", "0fei-3mfnv83kmfmv7jeekff-fmdg");
    }
}
