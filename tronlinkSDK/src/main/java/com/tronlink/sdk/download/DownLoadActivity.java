package com.tronlink.sdk.download;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.tronlink.sdk.R;


public class DownLoadActivity extends Activity {

    private static final String GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id=com.tronlink.wallet";
    private static final String LOCAL_APK_URL = "https://www.tronlink.org/download/android_TronLink/TronLink.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_download_apk);
        findViewById(R.id.tv_googleplay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToScan(GOOGLE_PLAY_URL);
            }
        });

        findViewById(R.id.tv_local_install).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToScan(LOCAL_APK_URL);
            }
        });
    }

    private void goToScan(String url){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        startActivity(intent);
    }
}
