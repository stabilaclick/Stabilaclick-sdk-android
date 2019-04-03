// ITronSDKInterface.aidl
package com.tron.wallet.bussiness.sdk.service;
// Declare any non-default types here with import statements

interface ITronSDKInterface {

    double getBalanceTrx();

    String getAccountJsonStr();

    String getResourceMessageJsonStr();

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
}
