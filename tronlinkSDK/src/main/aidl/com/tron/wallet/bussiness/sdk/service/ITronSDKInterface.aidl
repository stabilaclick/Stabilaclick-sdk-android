// ITronSDKInterface.aidl
package com.tron.wallet.bussiness.sdk.service;
// Declare any non-default types here with import statements

interface ITronSDKInterface {

    double getBalanceTrx(String address,boolean isBase58);

    String getAccountJsonStr(String address,boolean isBase58);

    String getResourceMessageJsonStr(String address,boolean isBase58);

    byte[] createTrxTransaction(String fromAddress,
                                         String toAddress, double amount);

    byte[] createTrc10Transaction(String fromAddress,
                                    String toAddress, double amount, String id);

    byte[] createTrc20Transaction(String fromAddress,
                                         String toAddress, double amount,
                                         int precision,
                                         String contractAddress);

    byte[] hashOperation(String hashStr);

    byte[] triggerContract(String fromAddress, String contractAddress, String methodName,
                                String paramsJson, String feeLimit, long amount);
}
