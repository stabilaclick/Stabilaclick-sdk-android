package com.tronlink.sdk.sdkinterface;

import android.app.Activity;
import android.content.Context;

import com.tronlink.sdk.bean.Account;
import com.tronlink.sdk.bean.ResourceMessage;


public interface ITronLinkSdk {

    void register(Context context);

    void unRegister(Context context);

    ResourceMessage getResourceMessage();

    Account getAccount();

    double getBalanceTrx();

    String createTransaction(int type, String fromAddress, String toAddress, double amount,
                             int precision, String id, String contractAddress);

    byte[] hashOperation(String hashStr);

    void authLogin(Activity activity);

    void toPay(Activity activity, String title, String site, String icon,
               String id, String contractAddress, String fromAddress,
               String toAddress, double amount, int type, int precision);
}
