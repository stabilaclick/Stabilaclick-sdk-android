package com.tronlink.sdk;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.tron.wallet.bussiness.sdk.service.ITronSDKInterface;
import com.tronlink.sdk.bean.Account;
import com.tronlink.sdk.bean.Param;
import com.tronlink.sdk.bean.ResourceMessage;
import com.tronlink.sdk.download.DownLoadActivity;
import com.tronlink.sdk.sdkinterface.ITronLinkSdk;
import com.tronlink.sdk.utils.AppUtils;

import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;

public class TronLinkSdk implements ITronLinkSdk {
    public static final String INTENT_LOGIN_RESULT = "intent_address_result";
    public static final String INTENT_PAY_RESULT = "pay_result";//1是白名单

    public static final String INTENT_ACTION = "intent_action";
    public static final String INTENT_ACTION_LOGIN = "intent_action_login";
    public static final String INTENT_ACTION_PAY = "intent_action_pay";
    public static final String INTENT_ACTION_TRIGGER_CONTRACT = "intent_action_trigger_contract";


    public static final int INTENT_LOGIN_REQUESTCODE = 10001;
    public static final int INTENT_PAY_REQUESTCODE = 10002;
    public static final int INTENT_TRIGGER_CONTRACT_REQUESTCODE = 10003;


    private static final String INTENT_PAY_PARAM_TYPE = "intent_param_type";
    private static final String INTENT_PAY_PARAM_FROM_ADDRESS = "intent_param_fromaddress";
    private static final String INTENT_PAY_PARAM_TO_ADDRESS = "intent_param_toaddress";
    private static final String INTENT_PAY_PARAM_ID = "intent_param_id";
    private static final String INTENT_PAY_PARAM_CONTRACT_ADDRESS = "intent_param_contract_address";
    private static final String INTENT_PAY_PARAM_TITLE = "intent_param_title";
    private static final String INTENT_PAY_PARAM_SITE = "intent_param_site";
    private static final String INTENT_PAY_PARAM_ICON = "intent_param_icon";
    private static final String INTENT_PAY_PARAM_AMOUNT = "intent_param_amount";
    private static final String INTENT_PAY_PARAM_PRECISION = "intent_param_precision";

    private static final String INTENT_PARAM_TRIGGER_CONTRACT = "intent_param_trigger_contract";

    private static final String ENTER_URI = "tronlink://account/enter";
    private ITronSDKInterface mStub;
    private static final String TAG = "TronLinkSdk";
    private Context mContext;

    private static class SingletonHolder {
        private static final TronLinkSdk INSTANCE = new TronLinkSdk();
    }

    private TronLinkSdk() {
    }

    public static final ITronLinkSdk getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void register(Context context) {
        mContext = context;
        Intent intent = new Intent();
        //由于是隐式启动Service 所以要添加对应的action，A和之前服务端的一样。
        intent.setAction("com.tronlink.wallet.TronSDKService");
        //android 5.0以后直设置action不能启动相应的服务，需要设置packageName或者Component。
        intent.setPackage("com.tronlink.wallet"); //packageName 需要和服务端的一致.
        context.bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void unRegister(Context context) {
        context.unbindService(serviceConnection);
    }

    @Override
    public ResourceMessage getResourceMessage(String address, boolean isBase58) {
        ResourceMessage resourceMessage = null;
        if (adjustNotEmpty()) {
            String jsonStr = null;
            try {
                jsonStr = mStub.getResourceMessageJsonStr(address, isBase58);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "resourceMessage:" + jsonStr);
            if (!TextUtils.isEmpty(jsonStr)) {
                resourceMessage = new Gson().fromJson(jsonStr, ResourceMessage.class);
            }
        }
        return resourceMessage;
    }

    @Override
    public Account getAccount(String address, boolean isBase58) {
        Account account = null;
        if (adjustNotEmpty()) {
            String jsonStr = null;
            try {
                jsonStr = mStub.getAccountJsonStr(address, isBase58);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "resourceMessage:" + jsonStr);
            if (!TextUtils.isEmpty(jsonStr)) {
                account = new Gson().fromJson(jsonStr, Account.class);

            }
        }
        return account;
    }

    @Override
    public double getBalanceTrx(String address, boolean isBase58) {
        if (adjustNotEmpty()) {
            try {
                return mStub.getBalanceTrx(address, isBase58);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public String createTrxTransaction(String fromAddress, String toAddress, double amount, int precision) {
        if (adjustNotEmpty()) {
            try {
                return mStub.createTrxTransaction(fromAddress, toAddress, amount, precision);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String createTrc10Transaction(String fromAddress, String toAddress, double amount, int precision, String id) {
        if (adjustNotEmpty()) {
            try {
                return mStub.createTrc10Transaction(fromAddress, toAddress, amount, precision, id);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String createTrc20Transaction(String fromAddress, String toAddress, double amount, int precision, String contractAddress) {
        if (adjustNotEmpty()) {
            try {
                return mStub.createTrc20Transaction(fromAddress, toAddress, amount, precision, contractAddress);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public byte[] hashOperation(String hashStr) {
        if (adjustNotEmpty()) {
            try {
                return mStub.hashOperation(hashStr);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private boolean adjustNotEmpty() {
        if (checkIsInstall()) {
            if (mStub == null) {
                AppUtils.jumpStartInterface(mContext);
                Log.d(TAG, "mStub is null");
                return false;
            }
        }
        return true;
    }

    private boolean checkIsInstall() {
        Intent intent = new Intent();
        intent.setData(Uri.parse(ENTER_URI));
        intent.putExtra(INTENT_ACTION, INTENT_ACTION_LOGIN);
        if (!AppUtils.isAppInstalled2(mContext, intent)) {
            //未安装app or 版本不支持schema
            Intent in = new Intent(mContext, DownLoadActivity.class);
            mContext.startActivity(in);
            boolean isActivity = mContext instanceof Activity;
            if (!isActivity) {
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            mContext.startActivity(in);
            return false;
        }
        return true;
    }

    @Override
    public void authLogin(Activity activity) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(ENTER_URI));
        intent.putExtra(INTENT_ACTION, INTENT_ACTION_LOGIN);
        if (AppUtils.isAppInstalled2(activity, intent)) {
            activity.startActivityForResult(intent, INTENT_LOGIN_REQUESTCODE);
        } else {
            //未安装app or 版本不支持schema
            Intent in = new Intent(activity, DownLoadActivity.class);
            activity.startActivity(in);
        }
    }

    @Override
    public void toPay(Activity activity, String title, String site, String icon,
                      String id, String contractAddress, String fromAddress,
                      String toAddress, double amount, int type, int precision) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(ENTER_URI));
        intent.putExtra(INTENT_ACTION, INTENT_ACTION_PAY);
        intent.putExtra(INTENT_PAY_PARAM_TITLE, title);
        intent.putExtra(INTENT_PAY_PARAM_SITE, site);
        intent.putExtra(INTENT_PAY_PARAM_ICON, icon);
        intent.putExtra(INTENT_PAY_PARAM_ID, id);
        intent.putExtra(INTENT_PAY_PARAM_CONTRACT_ADDRESS, contractAddress);
        intent.putExtra(INTENT_PAY_PARAM_FROM_ADDRESS, fromAddress);
        intent.putExtra(INTENT_PAY_PARAM_TO_ADDRESS, toAddress);
        intent.putExtra(INTENT_PAY_PARAM_AMOUNT, amount);
        intent.putExtra(INTENT_PAY_PARAM_TYPE, type);
        intent.putExtra(INTENT_PAY_PARAM_PRECISION, precision);
        if (AppUtils.isAppInstalled2(activity, intent)) {
            activity.startActivityForResult(intent, INTENT_PAY_REQUESTCODE);
        } else {
            //未安装app or 版本不支持schema
            Intent in = new Intent(activity, DownLoadActivity.class);
            activity.startActivity(in);
        }
    }

    @Override
    public String triggerContract(String fromAddress, String toAddress, String contractAddress,
                                String methodName, List<Param> params,
                                String freeLimit, long amount) {
        String json = null;
        if (params != null)
            json = new Gson().toJson(params);
        try {
            return mStub.triggerContract(fromAddress, toAddress, contractAddress, methodName,
                    json, freeLimit, amount);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void goToSignPage(Activity activity, String transtionJson){
        Intent intent = new Intent();
        intent.setData(Uri.parse(ENTER_URI));
        intent.putExtra(INTENT_ACTION, INTENT_ACTION_TRIGGER_CONTRACT);
        intent.putExtra(INTENT_PARAM_TRIGGER_CONTRACT, transtionJson);
        if (AppUtils.isAppInstalled2(activity, intent)) {
            activity.startActivityForResult(intent, INTENT_TRIGGER_CONTRACT_REQUESTCODE);
        } else {
            //未安装app or 版本不支持schema
            Intent in = new Intent(activity, DownLoadActivity.class);
            activity.startActivity(in);
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //调用asInterface()方法获得IMyAidlInterface实例
            mStub = ITronSDKInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
