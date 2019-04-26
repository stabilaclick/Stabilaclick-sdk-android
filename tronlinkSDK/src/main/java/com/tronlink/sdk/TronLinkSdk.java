package com.tronlink.sdk;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tron.wallet.bussiness.sdk.service.ITronSDKInterface;
import com.tronlink.sdk.bean.Account;
import com.tronlink.sdk.bean.Param;
import com.tronlink.sdk.bean.ResourceMessage;
import com.tronlink.sdk.download.DownLoadActivity;
import com.tronlink.sdk.sdkinterface.ITronLinkSdk;
import com.tronlink.sdk.utils.AppFrontBackUtils;
import com.tronlink.sdk.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;

public class TronLinkSdk implements ITronLinkSdk {
    public static final String INTENT_LOGIN_RESULT = "intent_address_result";
    public static final String INTENT_PAY_RESULT = "pay_result";//1是白名单

    public static final String INTENT_ACTION = "intent_action";
    public static final String INTENT_ACTION_LOGIN = "intent_action_login";
    public static final String INTENT_ACTION_PAY = "intent_action_pay";
    public static final String INTENT_ACTION_PAY_JSON = "intent_action_pay_json";
    public static final String INTENT_ACTION_PAY_JSON_RETURN_JSON = "intent_action_pay_json_return_json";


    public static final int INTENT_LOGIN_REQUESTCODE = 10001;
    public static final int INTENT_PAY_REQUESTCODE = 10002;
    public static final int INTENT_PAY_JSON_REQUESTCODE = 10003;
    public static final int INTENT_PAY_JSON_RETURN_JSON_REQUESTCODE = 10004;

    private static final String INTENT_PARAM_TRANSACTION_BYTES = "intent_transaction_byte";
    private static final String INTENT_PARAM_TRANSACTION_JSON = "intent_transaction_json";
    private static final String INTENT_PARAM_WALLETNAME = "intent_param_wallet_name";

    private static final String ENTER_URI = "tronlink://account/enter";
    private ITronSDKInterface mStub;
    private static final String TAG = "TronLinkSdk";
    private Context mApplication;

    private static final String INTENT_PARAM_PACKAGENAME = "intent_param_packagename";
    private static final String INTENT_PARAM_SECRET = "intent_param_secret";
    private static final String INTENT_PARAM_SIGN = "intent_param_sign";
    private static final String INTENT_PARAM_APPID = "intent_param_appid";

    private String mPackageName = "app_package_nametest";
    private String mSign = "sdsevhytv";
    private String mAppId;
    private String mSecret;

    private static class SingletonHolder {
        private static final TronLinkSdk INSTANCE = new TronLinkSdk();
    }

    private TronLinkSdk() {
    }

    public static final ITronLinkSdk getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void register(Application application, String appId, String secret) {
        mApplication = application;
//        mPackageName = AppUtils.getAppName(application);
        mAppId = appId;
        mSecret = secret;
        //mSign = SignUtils.getAppSignSha1(application);
        AppFrontBackUtils helper = new AppFrontBackUtils();
        helper.register(application, new AppFrontBackUtils.OnAppStatusListener() {
            @Override
            public void onFront() {
                //应用切到前台处理
                if (mStub == null)
                    bindService();
            }

            @Override
            public void onBack() {
                //应用切到后台处理

            }
        });

    }

    private void bindService() {
        Intent intent = new Intent();
        //由于是隐式启动Service 所以要添加对应的action，A和之前服务端的一样。
        intent.setAction("com.tronlink.wallet.TronSDKService");
        //android 5.0以后直设置action不能启动相应的服务，需要设置packageName或者Component。
        intent.setPackage("com.tronlink.wallet"); //packageName 需要和服务端的一致.
        mApplication.bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void wrapIntent(Intent intent) {
        intent.putExtra(INTENT_PARAM_PACKAGENAME, mPackageName);
        intent.putExtra(INTENT_PARAM_SECRET, mSecret);
        intent.putExtra(INTENT_PARAM_SIGN, mSign);
        intent.putExtra(INTENT_PARAM_APPID, mAppId);
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
                if (mStub != null && mStub.asBinder().isBinderAlive()) {
                    jsonStr = mStub.getResourceMessageJsonStr(address, isBase58);
                }
                else {
                    mStub = null;
                    bindService();
                }
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
                if (mStub != null && mStub.asBinder().isBinderAlive()) {
                    jsonStr = mStub.getAccountJsonStr(address, isBase58);
                }
                else {
                    mStub = null;
                    bindService();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "getAccount:" + jsonStr);
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
                if (mStub != null && mStub.asBinder().isBinderAlive()) {
                    return mStub.getBalanceTrx(address, isBase58);
                }
                else {
                    mStub = null;
                    bindService();
                }
                return 0;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public byte[] createTrxTransaction(String fromAddress, String toAddress, double amount) {
        if (adjustNotEmpty()) {
            try {
                if (mStub != null && mStub.asBinder().isBinderAlive()) {
                    return mStub.createTrxTransaction(fromAddress, toAddress, amount);
                }
                else {
                    mStub = null;
                    bindService();
                }
                return null;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public byte[] createTrc10Transaction(String fromAddress, String toAddress, double amount, String id) {
        if (adjustNotEmpty()) {
            try {
                if (mStub != null && mStub.asBinder().isBinderAlive()) {
                    return mStub.createTrc10Transaction(fromAddress, toAddress, amount, id);
                }
                else {
                    mStub = null;
                    bindService();
                }
                return null;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public byte[] createTrc20Transaction(String fromAddress, String toAddress, double amount, int precision, String contractAddress) {
        if (adjustNotEmpty()) {
            try {
                if (mStub != null && mStub.asBinder().isBinderAlive()) {
                    return mStub.createTrc20Transaction(fromAddress, toAddress, amount, precision, contractAddress);
                }
                else {
                    mStub = null;
                    bindService();
                }
                return null;
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
                if (mStub != null && mStub.asBinder().isBinderAlive()) {
                    return mStub.hashOperation(hashStr);
                }
                else {
                    mStub = null;
                    bindService();
                }
                return null;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private boolean adjustNotEmpty() {
        if (checkIsInstall()) {
            if (mStub == null) {
                AppUtils.jumpStartInterface(mApplication);
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
        if (!AppUtils.isAppInstalled(mApplication)) {
            goToDownloadPage();
        } else if (!AppUtils.isAppInstalled2(mApplication, intent)) {
            //未安装app or 版本不支持schema
            goToDownloadPage();
            Toast.makeText(mApplication, mApplication.getResources().getString(R.string.sdk_version_low_tip), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void goToDownloadPage() {
        Intent in = new Intent(mApplication, DownLoadActivity.class);
        boolean isActivity = mApplication instanceof Activity;
        if (!isActivity) {
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mApplication.startActivity(in);
    }

    @Override
    public void authLogin(Activity activity) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(ENTER_URI));
        intent.putExtra(INTENT_ACTION, INTENT_ACTION_LOGIN);
        wrapIntent(intent);
        if (AppUtils.isAppInstalled2(activity, intent)) {
            activity.startActivityForResult(intent, INTENT_LOGIN_REQUESTCODE);
        } else {
            //未安装app or 版本不支持schema
            Intent in = new Intent(activity, DownLoadActivity.class);
            activity.startActivity(in);
        }
    }

    @Override
    public void toPay(Activity activity, byte[] transactionBytes, String walletName) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(ENTER_URI));
        intent.putExtra(INTENT_ACTION, INTENT_ACTION_PAY);
        intent.putExtra(INTENT_PARAM_TRANSACTION_BYTES, transactionBytes);
        intent.putExtra(INTENT_PARAM_WALLETNAME, walletName);
        wrapIntent(intent);
        if (AppUtils.isAppInstalled2(activity, intent)) {
            activity.startActivityForResult(intent, INTENT_PAY_REQUESTCODE);
        } else {
            //未安装app or 版本不支持schema
            Intent in = new Intent(activity, DownLoadActivity.class);
            activity.startActivity(in);
        }
    }

    @Override
    public void toPay(Activity activity, String transtionJson, String walletName) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(ENTER_URI));
        intent.putExtra(INTENT_ACTION, INTENT_ACTION_PAY_JSON);
        intent.putExtra(INTENT_PARAM_TRANSACTION_JSON, transtionJson);
        intent.putExtra(INTENT_PARAM_WALLETNAME, walletName);
        wrapIntent(intent);
        if (AppUtils.isAppInstalled2(activity, intent)) {
            activity.startActivityForResult(intent, INTENT_PAY_JSON_REQUESTCODE);
        } else {
            //未安装app or 版本不支持schema
            Intent in = new Intent(activity, DownLoadActivity.class);
            activity.startActivity(in);
        }
    }

    @Override
    public void toPayReturnSign(Activity activity, String transtionJson, String walletName) {
        Intent intent = new Intent();
        intent.setData(Uri.parse(ENTER_URI));
        intent.putExtra(INTENT_ACTION, INTENT_ACTION_PAY_JSON_RETURN_JSON);
        intent.putExtra(INTENT_PARAM_TRANSACTION_JSON, transtionJson);
        intent.putExtra(INTENT_PARAM_WALLETNAME, walletName);
        wrapIntent(intent);
        if (AppUtils.isAppInstalled2(activity, intent)) {
            activity.startActivityForResult(intent, INTENT_PAY_JSON_RETURN_JSON_REQUESTCODE);
        } else {
            //未安装app or 版本不支持schema
            Intent in = new Intent(activity, DownLoadActivity.class);
            activity.startActivity(in);
        }
    }


    @Override
    public byte[] triggerContract(String fromAddress, String contractAddress,
                                  String methodName, List<Param> params,
                                  String freeLimit) {
        String jsonStr = "";
        if (params != null && params.size() > 0) {
            Gson gson = new Gson();
            JSONArray paramsArray = new JSONArray();
            for (int i = 0; i < params.size(); i++) {
                String accountStr = gson.toJson(params.get(i));
                JSONObject accountObject;
                try {
                    accountObject = new JSONObject(accountStr);
                    paramsArray.put(i, accountObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            jsonStr = paramsArray.toString();
        }
        try {
            return mStub.triggerContract(fromAddress, contractAddress, methodName,
                    jsonStr, freeLimit);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //调用asInterface()方法获得IMyAidlInterface实例
            mStub = ITronSDKInterface.Stub.asInterface(service);
            try {
                service.linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            try {
                if (mStub != null && mStub.asBinder().isBinderAlive()) {
                    mStub.verifySdk(mPackageName, mSecret,
                            mAppId, mSign);
                }
                else {
                    mStub = null;
                    bindService();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {

        @Override
        public void binderDied() {                  // 当绑定的service异常断开连接后，会自动执行此方法
            Log.e(TAG, "enter Service binderDied ");
            if (mStub != null) {
                mStub.asBinder().unlinkToDeath(mDeathRecipient, 0);
                bindService();
            }
        }
    };
}
