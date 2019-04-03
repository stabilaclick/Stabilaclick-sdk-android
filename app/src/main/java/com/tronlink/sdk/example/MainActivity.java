package com.tronlink.sdk.example;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tronlink.sdk.TronLinkSdk;
import com.tronlink.sdk.bean.Account;
import com.tronlink.sdk.bean.Param;
import com.tronlink.sdk.bean.ResourceMessage;
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
    private String mAddress;
    private static final String TAG = "MainActivity";
    private String mToAddress = "TX2rdGSexVCy6mAgDk3Vgk8La1FuheXTbF";
    private double mAmount = 0.01d;
    private ITronLinkSdk mTronSdk;
    private EditText mToAddressEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTronSdk = TronLinkSdk.getInstance();
        initView();
        initListener();
        mToAddressEt.setText(mToAddress);

    }

    private void initView() {
        mValueTv = findViewById(R.id.tv_value);
        mToAddressEt = findViewById(R.id.et_to_address);
    }

    private void initListener() {
        findViewById(R.id.bt_login).setOnClickListener(this);
        findViewById(R.id.bt_pay).setOnClickListener(this);
        findViewById(R.id.bt_getBalance).setOnClickListener(this);
        findViewById(R.id.bt_getAccount).setOnClickListener(this);
        findViewById(R.id.bt_getResourceMessage).setOnClickListener(this);
        findViewById(R.id.bt_operationHash).setOnClickListener(this);
        findViewById(R.id.bt_createTransation).setOnClickListener(this);
        findViewById(R.id.bt_triggerContract).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == INTENT_LOGIN_REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                if (data != null && data.getExtras() != null) {
                    mAddress = data.getStringExtra(INTENT_LOGIN_RESULT);
                    Toast.makeText(this, "login success, address:" + mAddress, Toast.LENGTH_LONG).show();
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
            if (data.getExtras() != null)
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
            case R.id.bt_pay:
                pay();
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
            case R.id.bt_createTransation:
                createTransactionTrx();
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
    private void pay() {
        if (TextUtils.isEmpty(mAddress)) {
            Toast.makeText(MainActivity.this, "未获取当前钱包地址", Toast.LENGTH_LONG).show();
        } else {
            TronLinkSdk.getInstance().toPay(MainActivity.this, "title", "http://www.baidu.com",
                    "", "", "", mAddress, mToAddress, mAmount, 0, 0);
        }
    }

    /**
     * get trx balance
     */
    private void getBalanceTrx() {
        if (TextUtils.isEmpty(mAddress)) {
            Toast.makeText(MainActivity.this, "未获取当前钱包地址", Toast.LENGTH_LONG).show();
        } else {
            final double balance = TronLinkSdk.getInstance().getBalanceTrx(mAddress, true);
            new Handler(getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mValueTv.setText(balance + " TRX");
                }
            });
        }
    }

    /**
     * get account info
     */
    private void getAccount() {
        if (TextUtils.isEmpty(mAddress)) {
            Toast.makeText(MainActivity.this, "未获取当前钱包地址", Toast.LENGTH_LONG).show();
        } else {
            new Thread(
            ) {
                @Override
                public void run() {
                    final Account account = TronLinkSdk.getInstance().getAccount(mAddress, true);
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
        if (TextUtils.isEmpty(mAddress)) {
            Toast.makeText(MainActivity.this, "未获取当前钱包地址", Toast.LENGTH_LONG).show();
        } else {
            new Thread(
            ) {
                @Override
                public void run() {
                    final ResourceMessage resourceMessage = TronLinkSdk.getInstance().getResourceMessage(mAddress, true);
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
    private void createTransactionTrx() {
        if (TextUtils.isEmpty(mAddress)) {
            Toast.makeText(MainActivity.this, "未获取当前钱包地址", Toast.LENGTH_LONG).show();
        } else {
            String transactionJson = TronLinkSdk.getInstance().createTrxTransaction(mAddress,
                    mToAddress,
                    0.01, 0);
            if (transactionJson != null)
                Log.d(TAG, transactionJson);
        }
    }

    /**
     * trigger contract
     */
    private void triggerContract() {
        if (TextUtils.isEmpty(mAddress)) {
            Toast.makeText(MainActivity.this, "未获取当前钱包地址", Toast.LENGTH_LONG).show();
        } else {
            String methodName = "transfer";
            String contractName = "";
            ArrayList params = new ArrayList();
            Param param = new Param();
            param.setParamType(Param.paramType.ADDRESS);
            param.setParamValue(mToAddress);
            params.add(param);
            Param param2 = new Param();
            param2.setParamType(Param.paramType.DOUBLE);
            param2.setParamValue("10");
            params.add(param2);
            String transactionJson = TronLinkSdk.getInstance().triggerContract(mAddress,
                    mToAddress, contractName, methodName, params, "1000000",
                    (long) (0.01 * 1000000));
            if (transactionJson != null)
                Log.d(TAG, transactionJson);
            mTronSdk.goToSignPage(MainActivity.this, transactionJson);
        }
    }
}
