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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static com.tronlink.sdk.TronLinkSdk.INTENT_LOGIN_REQUESTCODE;
import static com.tronlink.sdk.TronLinkSdk.INTENT_LOGIN_RESULT;
import static com.tronlink.sdk.TronLinkSdk.INTENT_PAY_JSON_REQUESTCODE;
import static com.tronlink.sdk.TronLinkSdk.INTENT_PAY_JSON_RETURN_JSON_REQUESTCODE;
import static com.tronlink.sdk.TronLinkSdk.INTENT_PAY_REQUESTCODE;
import static com.tronlink.sdk.TronLinkSdk.INTENT_PAY_RESULT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mValueTv;
    private Walllet mWallet;
    private static final String TAG = "MainActivity";
    private String mToAddress;// = "TX2rdGSexVCy6mAgDk3Vgk8La1FuheXTbF";
    private double mAmount;// = 0.01d;
    private int mPrecision;// = 0.01d;
    private String mContractAddress, mId;
    private ITronLinkSdk mTronSdk;
    private EditText mToAddressEt, mPayAmountEt, mIdEt, mContractAddressEt, mPrecisionEt, mNeedHashedAddressEt;
    private EditText mTriggerContractAddressEt, mTriggerContractMethodName, mTriggerContractParam1Et, mTriggerContractParam2PrecisionEt,
            mTriggerContractParam2AmountEt, mPayJsonEt;
    private static final String NO_ADDRESS = "Not getting current wallet address";

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
        mNeedHashedAddressEt = findViewById(R.id.et_need_hashed_address);
        mPayJsonEt = findViewById(R.id.et_pay_json);
//        String str = "{\"txID\":\"8cf4816c98706a1d7cde7bee3e74d7b57f3a0cc35d7bbc791022ac389e1ed6ef\",\"raw_data\":{\"contract\":[{\"parameter\":{\"value\":{\"data\":\"0a7bc885000000000000000000000000d51f8e9d2ef0a7317b758541b0a552706bedb3e80000000000000000000000000000000000000000000000000000000ccf91178000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010642a940000000000000000000000000000000000000000000000000000000000001386\",\"owner_address\":\"41a58269f525bf0bf7a80df7979a4775c6172e796c\",\"contract_address\":\"41b3bddae866b2ce2349bdcb59dfbfa1a75f8552da\",\"call_value\":100},\"type_url\":\"type.googleapis.com/protocol.TriggerSmartContract\"},\"type\":\"TriggerSmartContract\"}],\"ref_block_bytes\":\"bb46\",\"ref_block_hash\":\"f6e2284f3ae54ec5\",\"expiration\":1556246946000,\"fee_limit\":1000000000,\"timestamp\":1556246887501},\"raw_data_hex\":\"0a02bb462208f6e2284f3ae54ec540d0c1afbca52d5a9602081f1291020a31747970652e676f6f676c65617069732e636f6d2f70726f746f636f6c2e54726967676572536d617274436f6e747261637412db010a1541a58269f525bf0bf7a80df7979a4775c6172e796c121541b3bddae866b2ce2349bdcb59dfbfa1a75f8552da1894d590830122a4010a7bc885000000000000000000000000d51f8e9d2ef0a7317b758541b0a552706bedb3e80000000000000000000000000000000000000000000000000000000ccf91178000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010642a94000000000000000000000000000000000000000000000000000000000000138670cdf8abbca52d90018094ebdc03\"}";
//        mPayJsonEt.setText(str);

        mTriggerContractAddressEt = findViewById(R.id.et_trigger_contractaddress);
        mTriggerContractMethodName = findViewById(R.id.et_trigger_contract_methodName);
        mTriggerContractParam1Et = findViewById(R.id.et_trigger_params1_to_address);
        mTriggerContractParam2PrecisionEt = findViewById(R.id.et_trigger_params2_precision);
        mTriggerContractParam2AmountEt = findViewById(R.id.et_trigger_params2_amount);

    }

    private void getValue() {
        mToAddress = mToAddressEt.getText().toString();
        if (!TextUtils.isEmpty(mPayAmountEt.getText())) {
            try {
                mAmount = Double.parseDouble(mPayAmountEt.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
                mAmount = 0;
            }
        }
        if (!TextUtils.isEmpty(mPrecisionEt.getText())) {
            try {
                mPrecision = Integer.parseInt(mPrecisionEt.getText().toString());
            } catch (Exception e) {
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
        findViewById(R.id.bt_pay_json).setOnClickListener(this);
        findViewById(R.id.bt_pay_return_json).setOnClickListener(this);
        findViewById(R.id.bt_create_transaction_json_pay_trx).setOnClickListener(this);
        findViewById(R.id.bt_create_transaction_json_pay_trc10).setOnClickListener(this);
        findViewById(R.id.bt_create_transaction_json_pay_trc20).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == INTENT_LOGIN_REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                if (data != null && data.getStringExtra(INTENT_LOGIN_RESULT) != null) {
                    mWallet = new Gson().fromJson(data.getStringExtra(INTENT_LOGIN_RESULT), Walllet.class);
                    if (mWallet != null)
                        Toast.makeText(this, "login success, address:" + mWallet.getAddress(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "login cancel", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == INTENT_PAY_REQUESTCODE || requestCode == INTENT_PAY_JSON_REQUESTCODE) {
            boolean isSucc = false;
            if (data != null && data.getExtras() != null)
                isSucc = data.getBooleanExtra(INTENT_PAY_RESULT, false);
            Toast.makeText(this, "pay is " + (isSucc ? "success" : "fail"), Toast.LENGTH_LONG).show();
        }  else if (requestCode == INTENT_PAY_JSON_RETURN_JSON_REQUESTCODE) {
            String jsonStr = null;
            if (data != null && data.getExtras() != null)
                jsonStr = data.getStringExtra(INTENT_PAY_RESULT);
            mValueTv.setText(jsonStr);
            Toast.makeText(this, "return json:"+jsonStr, Toast.LENGTH_LONG).show();
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
            case R.id.bt_pay_json:
                payJson();
                break;
            case R.id.bt_pay_return_json:
                toPayReturnSign();
                break;
            case R.id.bt_create_transaction_json_pay_trx:
                createTrxTransactionJson(0);
                break;
            case R.id.bt_create_transaction_json_pay_trc10:
                createTrxTransactionJson(1);
                break;
            case R.id.bt_create_transaction_json_pay_trc20:
                createTrxTransactionJson(2);
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
        if (mWallet == null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, NO_ADDRESS, Toast.LENGTH_LONG).show();
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
        if (mWallet == null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, NO_ADDRESS, Toast.LENGTH_LONG).show();
        } else {
            final double balance = TronLinkSdk.getInstance().getBalanceTrx(mWallet.getAddress(), true);
            new Handler(getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mValueTv.setText(balance / 1000000 + " TRX");
                }
            });
        }
    }

    /**
     * get account info
     */
    private void getAccount() {
        if (mWallet == null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, NO_ADDRESS, Toast.LENGTH_LONG).show();
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
                                mValueTv.setText(new Gson().toJson(account));
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
        if (mWallet == null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, NO_ADDRESS, Toast.LENGTH_LONG).show();
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
                                mValueTv.setText(new Gson().toJson(resourceMessage));
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
        String address = mNeedHashedAddressEt.getText().toString();
        if (!TextUtils.isEmpty(address)) {
            mValueTv.setText(Arrays.toString(TronLinkSdk.getInstance().hashOperation(address)));
        } else {
            Toast.makeText(MainActivity.this, "The address that needs to be hashed cannot be empty", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * create transaction trx
     */
    private byte[] createTransactionTrx(int type) {
        getValue();
        if (mWallet == null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, NO_ADDRESS, Toast.LENGTH_LONG).show();
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
     * create transaction json trx
     */
    private String createTransactionJson(int type) {
        getValue();
        if (mWallet == null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, NO_ADDRESS, Toast.LENGTH_LONG).show();
            return null;
        } else {
            if (type == 0) {
                return TronLinkSdk.getInstance().createTrxTransactionJson(mWallet.getAddress(),
                        mToAddress,
                        mAmount);
            } else if (type == 1) {
                return TronLinkSdk.getInstance().createTrc10TransactionJson(mWallet.getAddress(),
                        mToAddress,
                        mAmount, mId);
            } else if (type == 2) {
                return TronLinkSdk.getInstance().createTrc20TransactionJson(mWallet.getAddress(),
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
        if (mWallet == null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, NO_ADDRESS, Toast.LENGTH_LONG).show();
        } else {
            String methodName = mTriggerContractMethodName.getText().toString();
            String contractAddress = mTriggerContractAddressEt.getText().toString();
            String toAddress = mTriggerContractParam1Et.getText().toString();
            int precision = Integer.parseInt(mTriggerContractParam2PrecisionEt.getText().toString());
            double amount = Double.parseDouble(mTriggerContractParam2AmountEt.getText().toString());

            ArrayList params = new ArrayList();
            Param param = new Param();
            param.setParamType(Param.paramType.ADDRESS);
            param.setParamValue(toAddress);
            params.add(param);
            Param param2 = new Param();
            param2.setParamType(Param.paramType.DOUBLE);
            double pow = Math.pow(10, precision);
            BigDecimal bigDecimal = new BigDecimal(amount * pow);
            param2.setParamValue(String.valueOf(bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP)));
            params.add(param2);
            byte[] transactionBytes = TronLinkSdk.getInstance().triggerContract(mWallet.getAddress(), contractAddress, methodName, params, "1000000");
            if (transactionBytes != null && transactionBytes.length > 0)
                mTronSdk.toPay(MainActivity.this, transactionBytes, mWallet.getName());
        }
    }

    private void payJson() {
        if (mWallet == null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, NO_ADDRESS, Toast.LENGTH_LONG).show();
        } else {
            String json = mPayJsonEt.getText().toString();
            if (!TextUtils.isEmpty(json)) {
                mTronSdk.toPay(MainActivity.this, json, mWallet.getName());
            }
            else {
                Toast.makeText(MainActivity.this, "Not create transaction json", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void toPayReturnSign() {
        if (mWallet == null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, NO_ADDRESS, Toast.LENGTH_LONG).show();
        } else {
            String json = mPayJsonEt.getText().toString();
            if (!TextUtils.isEmpty(json)) {
                mTronSdk.toPayReturnSign(MainActivity.this, json, mWallet.getName());
            }
            else {
                Toast.makeText(MainActivity.this, "Not create transaction json", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void createTrxTransactionJson(int type) {
        if (mWallet == null || TextUtils.isEmpty(mWallet.getAddress())) {
            Toast.makeText(MainActivity.this, NO_ADDRESS, Toast.LENGTH_LONG).show();
        } else {
            String json = createTransactionJson(type);
            mPayJsonEt.setText(json);
        }
    }
}
