## StabilaClickSDK-Android接入文档
[![](https://jitpack.io/v/StabilaClick/Stabilaclick-sdk-android.svg)](https://jitpack.io/#StabilaClick/Stabilaclick-sdk-android)
> [README DOC](README.md)
#### 说明：
1. StabilaClickSDK-Android 是帮助第三方app简化账户类操作，并提供查询，交易，签名等核心钱包逻辑的工具
2. StabilaClickSDK-Android 会提供对应的aar包，，您可以选择在线引入的方式（...），或者将我们提供的aar包放到对应的Studio - lib目录下，详细的接入请查看我们提供的官方Demo
3. StabilaClickSDK-Android 会尽可能的还原Stabila-web的语法机制 我们将提供 interface - IStabilaClickSdk 来简化操作流程，并对字段作出说明

----

#### API列表
1. void register(Context context);
- 释义：需要在项目启动时调用该方法，以便于建立通道
- 调用：
`StabilaClickSdk.getInstance().register(this);`


 2. void authLogin(Activity activity);
 - 释义：跳转账户登录页面，选择钱包地址
 - 调用：
`StabilaClickSdk.getInstance().authLogin(MainActivity.this);`

3. ResourceMessage getResourceMessage(String address,boolean isBase58);
- 释义：查询任意地址下的网络资源
- 参数1 address:当前钱包地址
- 参数2 isBase58 是否需要对钱包地址进行hash编码，一般传递true
- 调用：
`ResourceMessage resourceMessage = StabilaClickSdk.getInstance().getResourceMessage(mAddress, true);
`
- 注：该函数为耗时操作，建议在子线程执行
4. Account getAccount(String address,boolean isBase58);
- 释义：查询任意地址下的账户资源
- 参数1 address:当前钱包地址
- 参数2 isBase58 是否需要对钱包地址进行hash编码，一般传递true
- 调用：
`StabilaClickSdk.getInstance().getAccount(mAddress, true);`
- 注：该函数为耗时操作，建议在子线程执行

5. double getBalanceStb(String address,boolean isBase58);
- 释义：查询任意地址下的STB余额，单位：unit
- 参数1 address:当前钱包地址
- 参数2 isBase58 是否需要对钱包地址进行hash编码，一般传递true
- 调用：
`StabilaClickSdk.getInstance().getBalanceStb(mAddress, true);`
- 注：该函数为耗时操作，建议在子线程执行

6. byte[] createStbTransaction(String fromAddress,
String toAddress,
double amount);
- 释义：创建Stb转账交易,以byte数组形式返回
- 参数1 fromAddress: 当前钱包地址
- 参数2 toAddress: 接收者钱包地址
- 参数3 amount: 转账数量
- 调用：
`byte[] transationBytes = StabilaClickSdk.getInstance().createStbTransaction(mAddress,
mToAddress,
mAmount);
`
- 注：该函数为耗时操作，建议在子线程执行

7. byte[] createTrc10Transaction(String fromAddress,String toAddress, double amount, String id);
- 释义：创建TRC10转账, 以byte数组形式返回
- 参数1 fromAddress: 自己的钱包地址
- 参数2 toAddress: 接受者钱包地址
- 参数3 amount: 转账数量
- 参数4 id: TRC10的ID
- 调用：
`byte[] transationBytes = StabilaClickSdk.getInstance().createTrc10Transaction(mAddress, mToAddress, mAmount, mId);
`
- 注：该函数为耗时操作，建议在子线程执行

8. byte[] createTrc20Transaction(String fromAddress,
String toAddress,
double amount,
int precision,
String contractAddress
);
- 释义：创建TRC20转账, 以byte数组形式返回
- 参数1 fromAddress: 自己的钱包地址
- 参数2 toAddress: 接受者钱包地址
- 参数3 amount: 转账数量
- 参数4 precision: token精度
- 参数5 contractAddress: 合约地址
- 调用：
`byte[] transationBytes=StabilaClickSdk.getInstance().createTrc20Transaction(mAddress,mToAddress, mAmount, mPrecision, mContractAddress); `
- 注：该函数为耗时操作，建议在子线程执行


9. String createStbTransactionJson(String fromAddress,
String toAddress,
double amount);
- 释义：创建Stb转账交易, 以json字符串形式返回
- 参数1 fromAddress: 当前钱包地址
- 参数2 toAddress: 接收者钱包地址
- 参数3 amount: 转账数量
- 调用：
`String transactionJson = StabilaClickSdk.getInstance().createStbTransactionJson(mAddress,
mToAddress,
mAmount);
`
- 注：该函数为耗时操作，建议在子线程执行

10. String createTrc10TransactionJson(String fromAddress,String toAddress, double amount, String id);
- 释义：创建TRC10转账，以json字符串形式返回
- 参数1 fromAddress: 自己的钱包地址
- 参数2 toAddress: 接受者钱包地址
- 参数3 amount: 转账数量
- 参数4 id: TRC10的ID
- 调用：
`String transactionJson= StabilaClickSdk.getInstance().createTrc10TransactionJson(mAddress, mToAddress, mAmount, mId);
`
- 注：该函数为耗时操作，建议在子线程执行

11. String createTrc20Transaction(String fromAddress,
String toAddress,
double amount,
int precision,
String contractAddress
);
- 释义：创建TRC20转账，以json字符串形式返回
- 参数1 fromAddress: 自己的钱包地址
- 参数2 toAddress: 接受者钱包地址
- 参数3 amount: 转账数量
- 参数4 precision: token精度
- 参数5 contractAddress: 合约地址
- 调用：
`String transactionJson=StabilaClickSdk.getInstance().createTrc20TransactionJson(mAddress,mToAddress, mAmount, mPrecision, mContractAddress); `
- 注：该函数为耗时操作，建议在子线程执行


12. byte[] hashOperation(String hashStr)
- 释义：对钱包地址进行hash操作并以byte数组形式返回
- 参数1 hashStr: 需要进行hash操作的钱包地址
- 调用：
`String hashAddress = StabilaClickSdk.getInstance().hashOperation(address))`
- 注：该函数为耗时操作，建议在子线程执行

13. void toPay(Activity activity
, byte[] transactionBytes
, String walletName );
- 释义：跳转支付台，密码验证成功后进行广播
- 参数1 transactionBytes: 未签名transaction 的字节数组形式的数据
- 参数2 walletName: 当前钱包名称
- 调用：
`byte[] transationBytes=StabilaClickSdk.getInstance().createStbTransaction(mAddress,mToAddress, mAmount);
StabilaClickSdk.getInstance().toPay(MainActivity.this, transationBytes, "your wallet name");`

14. void toPay(Activity activity
, String transactionJson
, String walletName );
- 释义：跳转支付台，密码验证成功后进行广播
- 参数1 transactionJson: 未签名transaction 的json字符串形式数据
- 参数2 walletName: 当前钱包名称
- 调用：
`StabilaClickSdk.getInstance().toPay(MainActivity.this, transationJson, "your wallet name");`

15. void toPayReturnSign(Activity activity
, String json , String walletName
);
- 释义：跳转支付台，密码验证成功后返回签名后的transaction json字符串
- 参数1 json: 未签名的transaction json字符串
- 参数2 walletName: 当前钱包名称
调用：
`
StabilaClickSdk.getInstance().toPayReturnSign(MainActivity.this, transationJson, "your wallet name");
`

16. byte[] triggerContract(String fromAddress,
String contractAddress,
String methodName,
List params,
String freeLimit,
long amount);

- 释义：触发智能合约 - 并将transaction以字节数组形式返回
- 参数1 fromAddress： 当前钱包地址
- 参数2 contractAddress：合约地址
- 参数3 methodName：合约的方法名称
- 参数4 params: 合约的参数列表
- 参数5 freeLimit: 矿工费酬金的最大数值（单位unit）
- 参数6 amount: 合约注入的stb数量
调用：

```String methodName = "transfer";
String contractAddress = "";
ArrayList params = new ArrayList();
Param param = new Param();
param.setParamType(Param.paramType.ADDRESS);
param.setParamValue(mToAddress);
params.add(param);
Param param2 = new Param();
param2.setParamType(Param.paramType.DOUBLE);
param2.setParamValue("10");
params.add(param2);
Byte[] transactionBytes = StabilaClickSdk.getInstance().triggerContract(mWallet.getAddress(), contractAddress, methodName, params, "1000000",(long) (0.01 * 1000000));
if (transactionJson != null)
    Log.d(TAG, transactionJson);
StabilaClickSdk.getInstance().toPay(MainActivity.this, transactionBytes, mWallet.getName());
```