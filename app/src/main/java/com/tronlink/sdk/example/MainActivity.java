package com.tronlink.sdk.example;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tronlink.sdk.TronLinkSdk;
import com.tronlink.sdk.bean.Account;
import com.tronlink.sdk.bean.Param;
import com.tronlink.sdk.bean.ResourceMessage;
import com.tronlink.sdk.bean.Walllet;
import com.tronlink.sdk.sdkinterface.ITronLinkSdk;

import java.util.ArrayList;
import java.util.Arrays;

import static com.tronlink.sdk.TronLinkSdk.INTENT_LOGIN_REQUESTCODE;
import static com.tronlink.sdk.TronLinkSdk.INTENT_LOGIN_RESULT;
import static com.tronlink.sdk.TronLinkSdk.INTENT_PAY_REQUESTCODE;
import static com.tronlink.sdk.TronLinkSdk.INTENT_PAY_RESULT;
import static com.tronlink.sdk.TronLinkSdk.INTENT_TRIGGER_CONTRACT_REQUESTCODE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mValueTv;
    private Walllet mWallet;
    private static final String TAG = "MainActivity";
    private String mToAddress;// = "TX2rdGSexVCy6mAgDk3Vgk8La1FuheXTbF";
    private double mAmount;// = 0.01d;
    private int mPrecision;// = 0.01d;
    private String mContractAddress, mId;
    private ITronLinkSdk mTronSdk;
    private EditText mToAddressEt, mPayAmountEt, mIdEt, mContractAddressEt, mPrecisionEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTronSdk = TronLinkSdk.getInstance();
        initView();
        initListener();
    }

    private void initView() {
        mValueTv = findViewById(R.id.tv_value);
        mToAddressEt = findViewById(R.id.et_to_address);
        mPayAmountEt = findViewById(R.id.et_amount);
        mIdEt = findViewById(R.id.et_id);
        mContractAddressEt = findViewById(R.id.et_contractaddress);
        mPrecisionEt = findViewById(R.id.et_precision);
    }

    private void getValue(){
        mToAddress = mToAddressEt.getText().toString();
        if(!TextUtils.isEmpty(mPayAmountEt.getText())){
            try {
                mAmount = Double.parseDouble(mPayAmountEt.getText().toString());
            }catch (Exception e){
                e.printStackTrace();
                mAmount = 0;
            }
        }
        if(!TextUtils.isEmpty(mPrecisionEt.getText())){
            try {
                mPrecision = Integer.parseInt(mPrecisionEt.getText().toString());
            }catch (Exception e){
                e.printStackTrace();
                mPrecision = 0;
            }
        }
        mContractAddress = mContractAddressEt.getText().toString();
        mId = mIdEt.getText().toString();
    }

    private void initListener() {
        findViewById(R.id.bt_login).setOnClickListener(this);
        findViewById(R.id.bt_pay_trx).setOnClickListener(this);
        findViewById(R.id.bt_pay_trc10).setOnClickListener(this);
        findViewById(R.id.bt_pay_trc20).setOnClickListener(this);
        findViewById(R.id.bt_getBalance).setOnClickListener(this);
        findViewById(R.id.bt_getAccount).setOnClickListener(this);
        findViewById(R.id.bt_getResourceMessage).setOnClickListener(this);
        findViewById(R.id.bt_operationHash).setOnClickListener(this);
        findViewById(R.id.bt_triggerContract).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == INTENT_LOGIN_REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                if (data != null && data.getStringExtra(INTENT_LOGIN_RESULT) != null) {
                    mWallet = new Gson().fromJson(data.getStringExtra(INTENT_LOGIN_RESULT), Walllet.class);
                    if(mWallet!=null)
                        Toast.makeText(this, "login success, address:" + mWallet.getAddress(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "login cancel", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == INTENT_PAY_REQUESTCODE) {
            boolean isSucc = false;
            if (data.getExtras() != null)
                isSucc = data.getBooleanExtra(INTENT_PAY_RESULT, false);
            Toast.makeText(this, "pay is " + (isSucc ? "success" : "fail"), Toast.LENGTH_LONG).show();
        } else if (requestCode == INTENT_TRIGGER_CONTRACT_REQUESTCODE) {
            boolean isSucc = false;
            if (data!=null && data.getExtras() != null)
                isSucc = data.getBooleanExtra(INTENT_PAY_RESULT, false);
            Toast.makeText(this, "tragger contract is " + (isSucc ? "success" : "fail"), Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                login();
                break;
            case R.id.bt_pay_trx:
                pay(0);
                break;
            case R.id.bt_pay_trc10:
                pay(1);
                break;
            case R.id.bt_pay_trc20:
                pay(2);
                break;
            case R.id.bt_getBalance:
                getBalanceTrx();
                break;
            case R.id.bt_getAccount:
                getAccount();
                break;
            case R.id.bt_getResourceMessage:
                getResourceMessage();
                break;
            case R.id.bt_operationHash:
                operationHash();
                break;
            case R.id.bt_triggerContract:
                triggerContract();
                break;
        }

    }

    /**
     * login will return account address
     */
    private void login() {
        mTronSdk.authLogin(MainActivity.this);
    }

    /**
     * pay
     */
    private void pay(int type) {
        if (mWallet==null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, "未获取当前钱包地址", Toast.LENGTH_LONG).show();
        } else {
            byte[] transationBytes = createTransactionTrx(type);
            if (transationBytes != null)
                TronLinkSdk.getInstance().toPay(MainActivity.this, transationBytes, mWallet.getName());
        }
    }

    /**
     * get trx balance
     */
    private void getBalanceTrx() {
        if (mWallet==null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, "未获取当前钱包地址", Toast.LENGTH_LONG).show();
        } else {
            final double balance = TronLinkSdk.getInstance().getBalanceTrx(mWallet.getAddress(), true);
            new Handler(getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mValueTv.setText(balance/1000000 + " TRX");
                }
            });
        }
    }

    /**
     * get account info
     */
    private void getAccount() {
        if (mWallet==null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, "未获取当前钱包地址", Toast.LENGTH_LONG).show();
        } else {
            new Thread(
            ) {
                @Override
                public void run() {
                    final Account account = TronLinkSdk.getInstance().getAccount(mWallet.getAddress(), true);
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (account != null) {
                                mValueTv.setText(account.getBalance() + "");
                            }
                        }
                    });
                }
            }.start();
        }
    }

    /**
     * get resource message
     */
    private void getResourceMessage() {
        if (mWallet==null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, "未获取当前钱包地址", Toast.LENGTH_LONG).show();
        } else {
            new Thread(
            ) {
                @Override
                public void run() {
                    final ResourceMessage resourceMessage = TronLinkSdk.getInstance().getResourceMessage(mWallet.getAddress(), true);
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (resourceMessage != null)
                                mValueTv.setText(resourceMessage.getTotalEnergyLimit() + "");
                        }
                    });
                }
            }.start();
        }
    }

    /**
     * hash operation
     *
     * @return
     */
    private void operationHash() {
        mValueTv.setText(Arrays.toString(TronLinkSdk.getInstance().hashOperation(mToAddress)));
    }

    /**
     * create transaction trx
     */
    private byte[] createTransactionTrx(int type) {
        getValue();
        if (mWallet==null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, "未获取当前钱包地址", Toast.LENGTH_LONG).show();
            return null;
        } else {
            if (type == 0) {
                return TronLinkSdk.getInstance().createTrxTransaction(mWallet.getAddress(),
                        mToAddress,
                        mAmount);
            } else if (type == 1) {
                return TronLinkSdk.getInstance().createTrc10Transaction(mWallet.getAddress(),
                        mToAddress,
                        mAmount, mId);
            } else if (type == 2) {
                return TronLinkSdk.getInstance().createTrc20Transaction(mWallet.getAddress(),
                        mToAddress,
                        mAmount, mPrecision, mContractAddress);
            }
            return null;
        }
    }

    /**
     * trigger contract
     */
    private void triggerContract() {
        getValue();
        if (mWallet==null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, "未获取当前钱包地址", Toast.LENGTH_LONG).show();
        } else {
            String methodName = "transfer";
            ArrayList params = new ArrayList();
            Param param = new Param();
            param.setParamType(Param.paramType.ADDRESS);
            param.setParamValue(mToAddress);
            params.add(param);
            Param param2 = new Param();
            param2.setParamType(Param.paramType.DOUBLE);
            param2.setParamValue("10");
            params.add(param2);
            byte[] transactionBytes = TronLinkSdk.getInstance().triggerContract(mWallet.getAddress(), mContractAddress, methodName, params, "1000000",
                    (long) (0.01 * 1000000));
            if(transactionBytes!=null&&transactionBytes.length>0)
                mTronSdk.toPay(MainActivity.this, transactionBytes, mWallet.getName());
        }
    }
}
