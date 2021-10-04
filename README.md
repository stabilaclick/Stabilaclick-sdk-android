## StabilaClickSDK-Android Access document
[![](https://jitpack.io/v/StabilaClick/Stabilaclick-sdk-android.svg)](https://jitpack.io/#StabilaClick/Stabilaclick-sdk-android)

> [中文文档](README_zh.md)
#### Description：
1. StabilaClickSDK-Android  is a tool that helps third-party apps simplify account-level operations and provide core wallet logic for queries, transactions, signatures, etc.
2. StabilaClickSDK-Android will provide the corresponding aar package, you can choose the way to introduce online (...), or put the aar package we provide into the corresponding Studio - lib directory, please see us for detailed access. Official demo provided
3. StabilaClickSDK-Android will restore the syntax of Stabila-web as much as possible. We will provide interface - IStabilaClickSdk to streamline the operation and explain the fields.
----

#### API列表
1. void register(Context context);
- Interpretation: This method needs to be called when the project starts, in order to establish a channel
- Method call:
`StabilaClickSdk.getInstance().register(this);`


 2. void authLogin(Activity activity);
 - Interpretation: Jump account login page, select wallet address
 - Method call：
`StabilaClickSdk.getInstance().authLogin(MainActivity.this);`

3. ResourceMessage getResourceMessage(String address,boolean isBase58);
- Interpretation: query network resources under any address
- Parameter 1 address: current wallet address
- Parameter 2 isBase58 Whether to hash code the wallet address, generally pass true
- Method call：
`ResourceMessage resourceMessage = StabilaClickSdk.getInstance().getResourceMessage(mAddress, true);
`
- Note: This function is a time-consuming operation and is recommended to be executed in a child thread.

4. Account getAccount(String address,boolean isBase58); 
- Interpretation: Query account resources under any address
- Parameter 1 address: current wallet address
- Parameter 2 isBase58 Whether to hash code the wallet address, generally pass true
- Method call：
`StabilaClickSdk.getInstance().getAccount(mAddress, true);`
- Note: This function is a time-consuming operation and is recommended to be executed in a child thread.

5. double getBalanceStb(String address,boolean isBase58);
- Interpretation: Query the STB balance at any address, unit: unit
- Parameter 1 address: current wallet address
- Parameter 2 isBase58 Whether to hash code the wallet address, generally pass true
- Method call：
`StabilaClickSdk.getInstance().getBalanceStb(mAddress, true);` 
- Note: This function is a time-consuming operation and is recommended to be executed in a child thread.

6. byte[] createStbTransaction(String fromAddress,  
String toAddress,  
double amount); 
- Interpretation: Create a Stb transfer transaction, return as a byte array
- Parameter 1 fromAddress: Current wallet address
- Parameter 2 toAddress: Receiver wallet address
- Parameter 3 amount: Transfer amount
- Method call：
`byte[] transationBytes = StabilaClickSdk.getInstance().createStbTransaction(mAddress, 
mToAddress, 
mAmount);
`
- Note: This function is a time-consuming operation and is recommended to be executed in a child thread.

7. byte[] createTrc10Transaction(String fromAddress,String toAddress, double amount, String id);
- Interpretation: Create a TRC10 transfer, return as a byte array
- Parameter 1 fromAddress: own wallet address
- Parameter 2 toAddress: Recipient's wallet address
- Parameter 3 amount: Transfer amount
- Parameter 4 id: ID of TRC10
- Method call：
`byte[] transationBytes = StabilaClickSdk.getInstance().createTrc10Transaction(mAddress, mToAddress, mAmount, mId);
`
- Note: This function is a time-consuming operation and is recommended to be executed in a child thread.

8. byte[] createTrc20Transaction(String fromAddress, 
String toAddress,  
double amount, 
int precision, 
String contractAddress 
); 
- Interpretation: Create a TRC20 transfer, return as a byte array
- Parameter 1 fromAddress: own wallet address
- Parameter 2 toAddress: Recipient's wallet address
- Parameter 3 amount: Transfer amount
- Parameter 4 precision: token precision
- Parameter 5 contractAddress: contract address
- Method call：
`byte[] transationBytes=StabilaClickSdk.getInstance().createTrc20Transaction(mAddress,mToAddress, mAmount, mPrecision, mContractAddress); `
- Note: This function is a time-consuming operation and is recommended to be executed in a child thread.


9. String createStbTransactionJson(String fromAddress,  
String toAddress,  
double amount); 
- Interpretation: Create a Stb transfer transaction, return as a json string
- Parameter 1 fromAddress: Current wallet address
- Parameter 2 toAddress: Receiver wallet address
- Parameter 3 amount: Transfer amount
- Method call：
`String transactionJson = StabilaClickSdk.getInstance().createStbTransactionJson(mAddress, 
mToAddress, 
mAmount);
`
- Note: This function is a time-consuming operation and is recommended to be executed in a child thread.

10. String createTrc10TransactionJson(String fromAddress,String toAddress, double amount, String id);
- Interpretation: Create a TRC10 transfer, return as a json string
- Parameter 1 fromAddress: own wallet address
- Parameter 2 toAddress: Recipient's wallet address
- Parameter 3 amount: Transfer amount
- Parameter 4 id: ID of TRC10
- Method call：
`String transactionJson= StabilaClickSdk.getInstance().createTrc10TransactionJson(mAddress, mToAddress, mAmount, mId);
`
- Note: This function is a time-consuming operation and is recommended to be executed in a child thread.

11. String createTrc20Transaction(String fromAddress, 
String toAddress,  
double amount, 
int precision, 
String contractAddress 
); 
- Interpretation: Create a TRC20 transfer and return it as a json string
- Parameter 1 fromAddress: own wallet address
- Parameter 2 toAddress: Recipient's wallet address
- Parameter 3 amount: Transfer amount
- Parameter 4 precision: token precision
- Parameter 5 contractAddress: contract address
- Method call：
`String transactionJson=StabilaClickSdk.getInstance().createTrc20TransactionJson(mAddress,mToAddress, mAmount, mPrecision, mContractAddress); `
- Note: This function is a time-consuming operation and is recommended to be executed in a child thread.


12. byte[] hashOperation(String hashStr)
- Interpretation: hash operation on wallet address and return as byte array
- Parameter 1 hashStr: Wallet address for which hash operation is required
- Method call：
`String hashAddress = StabilaClickSdk.getInstance().hashOperation(address))`
- Note: This function is a time-consuming operation and is recommended to be executed in a child thread.

13. void toPay(Activity activity 
, byte[] transactionBytes
, String walletName ); 
- Interpretation: Jump to the payment station, broadcast after successful password verification
- Parameter 1 transactionBytes: data in the form of a byte array of unsigned transactions
- Parameter 2 walletName: current wallet name
- Method call：
`byte[] transationBytes=StabilaClickSdk.getInstance().createStbTransaction(mAddress,mToAddress, mAmount); 
StabilaClickSdk.getInstance().toPay(MainActivity.this, transationBytes, "your wallet name");`

14. void toPay(Activity activity 
, String transactionJson
, String walletName ); 
- Interpretation: Jump to the payment station, broadcast after successful password verification
- Parameter 1 transactionJson: json string form data for unsigned transaction
- Parameter 2 walletName: current wallet name
- Method call：
`StabilaClickSdk.getInstance().toPay(MainActivity.this, transationJson, "your wallet name");`
 
15. void toPayReturnSign(Activity activity 
, String json , String walletName
); 
- Interpretation: Jump payment desk, return the signed transaction json string after successful password verification
- Parameter 1 json: Unsigned transaction json string
- Parameter 2 walletName: current wallet name
Method call：
`
StabilaClickSdk.getInstance().toPayReturnSign(MainActivity.this, transationJson, "your wallet name");
`

16. byte[] triggerContract(String fromAddress, 
String contractAddress, 
String methodName, 
List params, 
String freeLimit, 
long amount); 

- Interpretation: Triggering a smart contract - and returning the transaction as a byte array
- Parameter 1 fromAddress: Current wallet address
- Parameter 2 contractAddress: contract address
- Parameter 3 methodName: the method name of the contract
- Parameter 4 params: List of parameters for the contract
- Parameter 5 freeLimit: Maximum value of miner's fee (unit: unit)
- Parameter 6 amount: The number of stb injected by the contract
Method call：

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
