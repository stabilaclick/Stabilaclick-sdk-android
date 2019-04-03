package com.tronlink.sdk.sdkinterface;

import android.app.Activity;
import android.content.Context;

import com.tronlink.sdk.bean.Account;
import com.tronlink.sdk.bean.Param;
import com.tronlink.sdk.bean.ResourceMessage;

import java.util.List;


public interface ITronLinkSdk {

    void register(Context context);

    void unRegister(Context context);

    ResourceMessage getResourceMessage(String address, boolean isBase58);

    Account getAccount(String address, boolean isBase58);

    double getBalanceTrx(String address, boolean isBase58);

    String createTrxTransaction(String fromAddress,
                                String toAddress, double amount,
                                int precision);

    String createTrc10Transaction(String fromAddress,
                                  String toAddress, double amount,
                                  int precision, String id);

    String createTrc20Transaction(String fromAddress,
                                  String toAddress, double amount,
                                  int precision,
                                  String contractAddress);

    byte[] hashOperation(String hashStr);

    void authLogin(Activity activity);

    void toPay(Activity activity, String title, String site, String icon,
               String id, String contractAddress, String fromAddress,
               String toAddress, double amount, int type, int precision);

    String triggerContract(String fromAddress, String toAddress, String contractAddress,
                           String methodName, List<Param> params,
                           String freeLimit, long amount);

    void goToSignPage(Activity activity, String json);

}
