//package com.ruoyi.cobo;
//
//import com.alibaba.fastjson2.JSON;
//import com.alibaba.fastjson2.JSONObject;
//import com.cobo.waas2.ApiClient;
//import com.cobo.waas2.ApiException;
//import com.cobo.waas2.Configuration;
//import com.cobo.waas2.Env;
//import com.cobo.waas2.api.TransactionsApi;
//import com.cobo.waas2.api.WalletsApi;
//import com.cobo.waas2.model.*;
//import com.ruoyi.common.utils.AesUtils;
//import com.ruoyi.common.utils.EncrypUtils;
//import com.ruoyi.system.service.ISysConfigService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
///**
// * 获取地址类
// *
// * @author admin
// */
//@Component
//public class CoboWaas2Service
//{
//	protected final Logger log = LoggerFactory.getLogger(CoboWaas2Service.class);
//
//
//    @Autowired
//    private ISysConfigService configService;
//
//    private boolean isProd;
//	private String coboIp;
//	private String kmsConfig;
//	private String coboPubKey;
//	private String secretKey;
//	private String walletId;
//
//	private String withdrawRequestIdPrefix = "udc_withdraw_";
//
//    public boolean isProd() {
//		return isProd;
//	}
//
//	public void setProd(boolean isProd) {
//		this.isProd = isProd;
//	}
//
//	public String getCoboIp() {
//		return coboIp;
//	}
//
//	public void setCoboIp(String coboIp) {
//		this.coboIp = coboIp;
//	}
//
//	public String getWithdrawRequestIdPrefix() {
//		return withdrawRequestIdPrefix;
//	}
//
//    @PostConstruct
//    private void init() {
//    	String config = this.configService.selectConfigByKey("cobo_config");
//		JSONObject json = JSON.parseObject(config);
//		this.isProd = json.getBoolean("is_prod");
//		this.coboIp = json.getString("cobo_ip");
//		this.kmsConfig = json.getString("kmsConfig");
//		if(json.containsKey("cobo_pub_key")) {
//			this.coboPubKey = EncrypUtils.decode("123456", json.getString("cobo_pub_key"));
//		}
//		if(json.containsKey("secret_key")) {
//			this.secretKey = EncrypUtils.decode("123456", json.getString("secret_key"));
//		}
//		if(json.containsKey("wallet_id")) {
//			this.walletId = EncrypUtils.decode("123456", json.getString("wallet_id"));
//		}
//		log.info("Cobo service init=====================================begin");
//		log.info("Cobo service init=====================================Env: {}", isProd?Env.PROD:Env.DEV);
//		log.info("Cobo service init=====================================coboIp: {}", coboIp);
//		log.info("Cobo service init=====================================kmsConfig: {}", kmsConfig);
////		log.info("Cobo service init=====================================coboPubKey: {}", coboPubKey);
////		log.info("Cobo service init=====================================secretKey: {}", secretKey);
////		log.info("Cobo service init=====================================walletId: {}", walletId);
//		log.info("Cobo service init=====================================end");
////		log.info("System.getProperty(\"java.library.path\"): " + System.getProperty("java.library.path"));
//    }
//
//
////    public static void main(String[] args) {
////        ApiClient defaultClient = Configuration.getDefaultApiClient();
////        // Use the development environment
////        defaultClient.setEnv(Env.DEV);
////        // Replace `<YOUR_API_SECRET>` with your API secret
////        defaultClient.setPrivKey("68bf47592a0c7ade807b4c0506e059481182836886200177710b24d30729e5f3");
////        WalletsApi apiInstance = new WalletsApi();
////        try {
////            CreateCustodialWalletParams params = new CreateCustodialWalletParams()
////                    .name("Test_Wallet")
////                    .walletType(WalletType.CUSTODIAL)
////                    .walletSubtype(WalletSubtype.ASSET);
////            // Create an Asset Wallet
////            CreatedWalletInfo result = apiInstance.createWallet(new CreateWalletParams(params));
////            System.out.println(result);
////        } catch (ApiException e) {
////            log.info("Exception when calling WalletsApi#createWallet");
////            log.info("Status code: " + e.getCode());
////            log.info("Reason: " + e.getResponseBody());
////            log.info("Response headers: " + e.getResponseHeaders());
////            e.printStackTrace();
////        }
////    }
//
//    public static void main(String[] args) {
////    	{
////            ApiClient defaultClient = Configuration.getDefaultApiClient();
//////          defaultClient.setEnv(Env.DEV);
////          defaultClient.setEnv(Env.PROD);
//////          defaultClient.setPrivKey("68bf47592a0c7ade807b4c0506e059481182836886200177710b24d30729e5f3");//DEV
////          defaultClient.setPrivKey("99e805fc9c50d97aa211e1448a5576b9bf0985c809011013deb8ec97cc98831f");//PROD
////          WalletsApi apiInstance = new WalletsApi();
////          try {
//////              UUID wallet_id = UUID.fromString("b5a6ff09-9f8b-4a1a-b908-df38122e427b");//DEV
////              UUID wallet_id = UUID.fromString("d270f991-0bba-4e1a-b8b5-a13abed939ef");//PROD
////              CreateAddressRequest params = new CreateAddressRequest()
////                      .chainId("TRON")
////                      .count(1)
//////              		.encoding(null)
////              ;
////              System.out.println("params: " + params.toJson());
////              // Generate two addresses on the Bitcoin testnet3 (XTN) chain using P2TR encoding
////              List<AddressInfo> result = apiInstance.createAddress(wallet_id, params);
////              if(result!=null && result.size()>0) {
////              	AddressInfo addressInfo = result.get(0);
////              	System.out.println(addressInfo);
////              }
//////              for (AddressInfo addressInfo : result)
//////                  System.out.println(addressInfo);
////          } catch (ApiException e) {
//////              log.info("Exception when calling WalletsApi#createAddress");
//////              log.info("Status code: " + e.getCode());
//////              log.info("Reason: " + e.getResponseBody());
//////              log.info("Response headers: " + e.getResponseHeaders());
////              e.printStackTrace();
////          }
////    	}
//
//
//
////    	{
////    		ApiClient defaultClient = Configuration.getDefaultApiClient();
//////          defaultClient.setEnv(Env.DEV);
////            defaultClient.setEnv(Env.PROD);
//////            defaultClient.setPrivKey("68bf47592a0c7ade807b4c0506e059481182836886200177710b24d30729e5f3");//DEV
////            defaultClient.setPrivKey("99e805fc9c50d97aa211e1448a5576b9bf0985c809011013deb8ec97cc98831f");//PROD
////            TransactionsApi apiInstance = new TransactionsApi();
////
////            try {
////    	        UUID transactionId = UUID.fromString("43700c2a-0e4f-4c03-b799-d15a1378be1a");
////    	        TransactionDetail transaction = apiInstance.getTransactionById(transactionId);
////    	        if(transaction!=null) {
////    	        	System.out.println(transaction.toJson());
//////    	        	log.info("isValidAddress:{}", transactionDetail.toJson());
////    	        }
////
////    	        if(transaction!=null
////    		    		&& transaction.getStatus().getValue().equalsIgnoreCase(TransactionStatus.COMPLETED.getValue())) {
////    	        	System.out.println("success");
////    	        	System.out.println(transaction.getTransactionHash());
////    	        }else if(transaction!=null
////    		    		&& transaction.getStatus().getValue().equalsIgnoreCase(TransactionStatus.FAILED.getValue())) {
////    	        	System.out.println("fail");
////    	        }
////
////    	    } catch (ApiException e) {
//////    	        log.info("Exception when calling TransactionsApi#getTransactionById");
//////    	        log.info("Status code: " + e.getCode());
//////    	        log.info("Reason: " + e.getResponseBody());
//////    	        log.info("Response headers: " + e.getResponseHeaders());
////    	        e.printStackTrace();
////    	    }
////    	}
//
//
//
////    	{
////            ApiClient defaultClient = Configuration.getDefaultApiClient();
////            defaultClient.setEnv(Env.DEV);
////            defaultClient.setPrivKey("68bf47592a0c7ade807b4c0506e059481182836886200177710b24d30729e5f3");
////            TransactionsApi apiInstance = new TransactionsApi();
////            try {
////                UUID walletId = UUID.fromString("b5a6ff09-9f8b-4a1a-b908-df38122e427b");
////
////                TransferParams params = new TransferParams();
////                params.setRequestId("sss_withdraw_2");
////
////                CustodialTransferSource custodialTransferSource = new CustodialTransferSource().sourceType(WalletSubtype.ASSET).walletId(walletId);
////                params.setSource(new TransferSource(custodialTransferSource));
////
////                params.setTokenId("BSC_USDT");
////                AddressTransferDestination addressTransferDestination = new AddressTransferDestination()
////                        .destinationType(TransferDestinationType.ADDRESS)
////                        .accountOutput(new AddressTransferDestinationAccountOutput()
////                                .address("0xa260880f34d421a5d450ca86a59de33b8d89b1d2")
////                                .amount(new BigDecimal("0.0002").stripTrailingZeros().toPlainString()));
////                params.setDestination(new TransferDestination(addressTransferDestination));
////
////                List<String> categoryNames = new ArrayList<>();
////                categoryNames.add("SSS");
////                params.categoryNames(categoryNames).description("sss_withdraw");
////
////                // Transfer Bitcoin testnet3(XTN) token from the wallet
////                CreateTransferTransaction201Response result = apiInstance.createTransferTransaction(params);
////            	System.out.println("withdraw: " + result);
////            	System.out.println("withdraw: " + result.toString());
////            	System.out.println("withdraw: " + result.toJson());
////            	if(result!=null) {
////            		System.out.println("transactionId: " + result.getTransactionId());
////            	}
////            } catch (ApiException e) {
//////                log.info("Exception when calling TransactionsApi#createTransferTransaction");
//////                log.info("Status code: " + e.getCode());
//////                log.info("Reason: " + e.getResponseBody());
//////                log.info("Response headers: " + e.getResponseHeaders());
////                e.printStackTrace();
////            }
////
////
////    	}
//
//
//
////    	params: {"chain_id":"BSC_BNB","count":1,"taproot_script_tree_hashes":[]}
////    	class AddressInfo {
////    	    address: 0x7969857c4f9294a73e89d5c5c2d3f4039277c04f
////    	    chainId: BSC_BNB
////    	    memo:
////    	    path:
////    	    encoding: ENCODING_DEFAULT
////    	    pubkey:
////    	    xOnlyPubkey:
////    	    rootPubkey:
////    	    taprootScriptTreeHash:
////    	    taprootInternalAddress:
////    	    additionalProperties: null
////    	}
//
////    	params: {"chain_id":"BSC_BNB","count":1,"taproot_script_tree_hashes":[]}
////    	class AddressInfo {
////    	    address: 0x9ac0e65f980a2d1624139624b46db50e613965db
////    	    chainId: BSC_BNB
////    	    memo:
////    	    path:
////    	    encoding: ENCODING_DEFAULT
////    	    pubkey:
////    	    xOnlyPubkey:
////    	    rootPubkey:
////    	    taprootScriptTreeHash:
////    	    taprootInternalAddress:
////    	    additionalProperties: null
////    	}
//
//
//
//    	// 生产+正式cobo
////    	walletid: d270f991-0bba-4e1a-b8b5-a13abed939ef
////    	System.out.println("coboPubKey: " + AesUtils.encode("aabb123", "45c2456c4c686bcfee5828ead4340ebd475e5c1b913d835a456eba3fd3a00bdc"));
////    	System.out.println("secretKey: " + AesUtils.encode("aabb123", "99e805fc9c50d97aa211e1448a5576b9bf0985c809011013deb8ec97cc98831f"));
////    	System.out.println("walletId: " + AesUtils.encode("aabb123", "d270f991-0bba-4e1a-b8b5-a13abed939ef"));
////    	System.out.println("coboIp: " + AesUtils.encode("aabb123", "127.0.0.1"));
//    	//{"is_prod":true,"secret_key":"vJJ6ytgopDTOkMPva8KQI7Fv01AdYFZjpi5InP1SwXADcEXn/+aPWGMIkyBpmE5Uk9Rs/sFG9p/Kv5Q5SAcLUt/0OeAoh76jOVSFXiS4MAU=","cobo_pub_key":"+3+kIjpW2yzcm1JWZtfcbhl2cwl12ps4U2y+6OmRA313lPaSTCtKsRcDShvuDFbNtoSALFj0VoZ6NBqZrkD6cN/0OeAoh76jOVSFXiS4MAU=","cobo_ip":"Q9rj875tOwNFcdow/fM5kA==","wallet_id":"qbslCiayqOwrSSU4TV0UpXLz3dMTyNPMN/ZL+6EI6PE61qqDGXttt5raxIzXZhSp"}
//
//
//    	// 生产+测试cobo
////    	System.out.println("coboPubKey: " + AesUtils.encode("aabb123", "f21353d8c28e27d7e883947d182e249ede376699adddc7c71765174a0bbfaf46"));
////    	System.out.println("secretKey: " + AesUtils.encode("aabb123", "68bf47592a0c7ade807b4c0506e059481182836886200177710b24d30729e5f3"));
////    	System.out.println("walletId: " + AesUtils.encode("aabb123", "b5a6ff09-9f8b-4a1a-b908-df38122e427b"));
////    	System.out.println("coboIp: " + AesUtils.encode("aabb123", "127.0.0.1"));
//    	//{"is_prod":false,"secret_key":"bNYzsozqVG2+VEHVILisXgQBR7SVWSSK0FBkl0zeg4xxnwVWBGFNtg2+qAhHeeny8LvLjAXtCVvK4hne+lKwsN/0OeAoh76jOVSFXiS4MAU=","cobo_pub_key":"7U3bw/a9M6KaQ0Hi4+I1WR9zDYrgs0/Xt5wUKqz/0nCtQGHkv8eyP3eqcyAOMmGwYTcRYMz/4FT2WUmI2I1Z6N/0OeAoh76jOVSFXiS4MAU=","cobo_ip":"Q9rj875tOwNFcdow/fM5kA==","wallet_id":"UqmyyCgBABg2sGaW4zHX8/xfBTV4YbiFEcjXeKK18yUgZgaGfr5wIcO5b0k1jKxJ"}
//
//
//    	// 本地+测试cobo
////    	System.out.println("coboPubKey: " + AesUtils.encode("123456", "f21353d8c28e27d7e883947d182e249ede376699adddc7c71765174a0bbfaf46"));
////    	System.out.println("secretKey: " + AesUtils.encode("123456", "68bf47592a0c7ade807b4c0506e059481182836886200177710b24d30729e5f3"));
////    	System.out.println("walletId: " + AesUtils.encode("123456", "b5a6ff09-9f8b-4a1a-b908-df38122e427b"));
////    	System.out.println("coboIp: " + AesUtils.encode("123456", "127.0.0.1"));
//    	//{"is_prod":false,"secret_key":"dKrU4JIAighS7dliiw40cV36pnZftVvOIZhPSlqkRAo0NX/Ct6Q/sacVKovYdF/qVrssriWVzkpXYMyW0hTASstTuWa7AIUi4zc39ExjtOc=","cobo_pub_key":"xxH166RDMzc9BDy5jxIgDlrcbkfhmPDWEFq4UJjVNGbm0NKUz2jwOzb5TyIKaWz+3edWEhvHwu4/fycFXKH8PstTuWa7AIUi4zc39ExjtOc=","cobo_ip":"orXKflWcDgJhstp3QFLTAw==","wallet_id":"Kgr3ULjsvuVTTh6RBrve60iM6BiCN37K6hUfWufcMkycDW9CkujSYHRFnaIZOr2Z"}
//
//
//    }
//
//    public String newAddress(String chainId){
//    	ApiClient defaultClient = Configuration.getDefaultApiClient();
//        defaultClient.setEnv(isProd?Env.PROD:Env.DEV);
//        String _secretKey = this.secretKey;
//        String _walletId = this.walletId;
//
//        defaultClient.setPrivKey(_secretKey);
//        WalletsApi apiInstance = new WalletsApi();
//        try {
//            UUID wallet_id = UUID.fromString(_walletId);
//            CreateAddressRequest params = new CreateAddressRequest()
//                    .chainId(chainId)
//                    .count(1)
////            		.encoding(null)
//            ;
//        	log.info("Cobo | newAddress params: {}", params.toJson());
//            // Generate two addresses on the Bitcoin testnet3 (XTN) chain using P2TR encoding
//            List<AddressInfo> result = apiInstance.createAddress(wallet_id, params);
//        	log.info("Cobo | newAddress result: {}", result==null?"no":JSONObject.toJSONString(result));
//            if(result!=null && result.size()>0) {
//            	AddressInfo addressInfo = result.get(0);
//            	return addressInfo.getAddress();
//            }
////            for (AddressInfo addressInfo : result)
////                System.out.println(addressInfo);
//
//        } catch (ApiException e) {
//        	log.info("Exception when calling WalletsApi#createAddress");
//        	log.info("Status code: {}", e.getCode());
//        	log.info("Reason: {}", e.getResponseBody());
//        	log.info("Response headers: {}", e.getResponseHeaders());
//            e.printStackTrace();
//        }
//
//        return null;
//
//    }
//
//    public Boolean isValidAddress(String chainId, String address){
//    	ApiClient defaultClient = Configuration.getDefaultApiClient();
//        defaultClient.setEnv(isProd?Env.PROD:Env.DEV);
//        String _secretKey = this.secretKey;
//
//        defaultClient.setPrivKey(_secretKey);
//        WalletsApi apiInstance = new WalletsApi();
//        try {
//            CheckAddressValidity200Response checkAddressValidity200Response = apiInstance.checkAddressValidity(chainId, address);
//        	if(checkAddressValidity200Response==null) {
////        		throw new ServiceException("Cobo | isValidAddress error");
//            	log.info("Cobo | isValidAddress error");
//            	return false;
//            }
//        	log.info("isValidAddress:{}", checkAddressValidity200Response.toJson());
//            return checkAddressValidity200Response.getValidity();
//
//        } catch (ApiException e) {
//        	log.info("Exception when calling WalletsApi#createAddress");
//        	log.info("Status code: {}", e.getCode());
//        	log.info("Reason: {}", e.getResponseBody());
//        	log.info("Response headers: {}", e.getResponseHeaders());
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//
//    public String withdraw(Long userCoinWithdrawId, String toAddress, BigDecimal amount, String tokenId){
//    	ApiClient defaultClient = Configuration.getDefaultApiClient();
//        defaultClient.setEnv(isProd?Env.PROD:Env.DEV);
//        String _secretKey = this.secretKey;
//        String _walletId = this.walletId;
//
//        defaultClient.setPrivKey(_secretKey);
//        TransactionsApi apiInstance = new TransactionsApi();
//        try {
//            UUID walletId = UUID.fromString(_walletId);
//
//            TransferParams params = new TransferParams();
//            params.setRequestId(this.withdrawRequestIdPrefix + userCoinWithdrawId);
//
//            CustodialTransferSource custodialTransferSource = new CustodialTransferSource().sourceType(WalletSubtype.ASSET).walletId(walletId);
//            params.setSource(new TransferSource(custodialTransferSource));
//
//            params.setTokenId(tokenId);
//            AddressTransferDestination addressTransferDestination = new AddressTransferDestination()
//                    .destinationType(TransferDestinationType.ADDRESS)
//                    .accountOutput(new AddressTransferDestinationAccountOutput()
//                            .address(toAddress)
//                            .amount(amount.stripTrailingZeros().toPlainString()))
//                    .forceExternal(true);
//            params.setDestination(new TransferDestination(addressTransferDestination));
//
//            List<String> categoryNames = new ArrayList<>();
//            categoryNames.add("udc");
//            params.categoryNames(categoryNames).description("udc_withdraw");
//
//            // Transfer Bitcoin testnet3(XTN) token from the wallet
//            CreateTransferTransaction201Response result = apiInstance.createTransferTransaction(params);
//        	log.info("withdraw:{}", result.toJson());
//    		return result.getTransactionId();
//        } catch (ApiException e) {
//            log.info("Exception when calling TransactionsApi#createTransferTransaction");
//            log.info("Status code: " + e.getCode());
//            log.info("Reason: " + e.getResponseBody());
//            log.info("Response headers: " + e.getResponseHeaders());
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    public TransactionDetail getTransactionByRequestId(String transaction_id){
//    	ApiClient defaultClient = Configuration.getDefaultApiClient();
//        defaultClient.setEnv(isProd?Env.PROD:Env.DEV);
//        String _secretKey = this.secretKey;
//
//        defaultClient.setPrivKey(_secretKey);
//        TransactionsApi apiInstance = new TransactionsApi();
//
//        try {
//	        UUID transactionId = UUID.fromString(transaction_id);
//	        TransactionDetail transactionDetail = apiInstance.getTransactionById(transactionId);
//	        if(transactionDetail!=null) {
//	        	log.info("isValidAddress:{}", transactionDetail.toJson());
//		    	return transactionDetail;
//	        }
//
//	    } catch (ApiException e) {
//	        log.info("Exception when calling TransactionsApi#getTransactionById");
//	        log.info("Status code: " + e.getCode());
//	        log.info("Reason: " + e.getResponseBody());
//	        log.info("Response headers: " + e.getResponseHeaders());
//	        e.printStackTrace();
//	    }
//
//        return null;
//    }
//
//}
