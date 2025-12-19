package com.ruoyi.business.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aliyun.cloudauth_intl20220809.models.CheckResultResponse;
import com.aliyun.cloudauth_intl20220809.models.InitializeResponse;
import com.aliyun.tea.TeaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class AliyunKycService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * <b>description</b> :
     * <p>使用凭据初始化账号 Client</p>
     * @return Client
     *
     * @throws Exception
     */
    public static com.aliyun.cloudauth_intl20220809.Client createClient() throws Exception {
        // 工程代码建议使用更安全的无 AK 方式，凭据配置方式请参见：https://help.aliyun.com/document_detail/378657.html。
        com.aliyun.credentials.Client credential = new com.aliyun.credentials.Client();
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                .setAccessKeyId("")
                .setAccessKeySecret("")
                .setCredential(credential);
        // Endpoint 请参考 https://api.aliyun.com/product/Cloudauth-intl
        config.endpoint = "cloudauth-intl.cn-hongkong.aliyuncs.com";
        return new com.aliyun.cloudauth_intl20220809.Client(config);
    }

    public static void main(String[] args_) throws Exception {

        com.aliyun.cloudauth_intl20220809.Client client = AliyunKycService.createClient();
        com.aliyun.cloudauth_intl20220809.models.InitializeRequest initializeRequest = new com.aliyun.cloudauth_intl20220809.models.InitializeRequest();

        String metainfo="{\"bioMetaInfo\":\"4.1.0:2916352,0\",\"deviceType\":\"h5\",\"ua\":\"Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 Mobile/15E148 Safari/604.1\"}";

        initializeRequest.setProductCode("eKYC");
        initializeRequest.setMerchantBizId("asdfasdfasdfadsfasdfasdfasdfasdf");
        initializeRequest.setMetaInfo(metainfo);
        initializeRequest.setMerchantUserId("1234");
        initializeRequest.setDocType("00000001");
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            // Copy the code to run, please print the return value of the API by yourself.
            InitializeResponse initializeResponse = client.initializeWithOptions(initializeRequest, runtime);
            System.out.println(JSON.toJSONString(initializeResponse));
        } catch (TeaException error) {
            // Only a printing example. Please be careful about exception handling and do not ignore exceptions directly in engineering projects.
            // print error message
            System.out.println(error.getMessage());
            // Please click on the link below for diagnosis.
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // Only a printing example. Please be careful about exception handling and do not ignore exceptions directly in engineering projects.
            // print error message
            System.out.println(error.getMessage());
            // Please click on the link below for diagnosis.
            System.out.println(error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
    }


    public  InitializeResponse initialize(String productCode,JSONObject metaObj, String verifiedType, String userId, String mechanId) {

        try {
            com.aliyun.cloudauth_intl20220809.Client client = AliyunKycService.createClient();
            com.aliyun.cloudauth_intl20220809.models.InitializeRequest initializeRequest = new com.aliyun.cloudauth_intl20220809.models.InitializeRequest();

            String metainfo="{\"bioMetaInfo\":\"4.1.0:2916352,0\",\"deviceType\":\"h5\",\"ua\":\"Mozilla/5.0 (iPhone; CPU iPhone OS 16_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.6 Mobile/15E148 Safari/604.1\"}";

            if(metaObj!=null){
                metainfo=JSON.toJSONString(metaObj);
            }

//            if(verifiedType.equals("id_card")){
//                initializeRequest.setProductCode("eKYC");
//                initializeRequest.setDocType("00000001");
//            }else{
//                initializeRequest.setProductCode("eKYC_PRO");
//                initializeRequest.setDocType("GLB03002");
//            }
            if(productCode.equalsIgnoreCase("eKYC_PRO")){
                initializeRequest.setSecurityLevel("01");
            }
            initializeRequest.setProductCode(productCode);
            initializeRequest.setDocType(verifiedType);

            initializeRequest.setMerchantBizId(mechanId);
            initializeRequest.setMetaInfo(metainfo);
            initializeRequest.setMerchantUserId(userId);
            initializeRequest.setEditOcrResult("0");

            com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();

            InitializeResponse initializeResponse = client.initializeWithOptions(initializeRequest, runtime);
            return initializeResponse;

        }catch (Exception error) {
            logger.error("initialize",error);
        }
        return null;

    }


    public CheckResultResponse checkResult(String merchantbizid, String transactionid)  {


        try {
            com.aliyun.cloudauth_intl20220809.Client client = AliyunKycService.createClient();
            com.aliyun.cloudauth_intl20220809.models.CheckResultRequest checkResultRequest = new com.aliyun.cloudauth_intl20220809.models.CheckResultRequest();
            com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();

            checkResultRequest.setMerchantBizId(merchantbizid);
            checkResultRequest.setIsReturnImage("Y");
            checkResultRequest.setTransactionId(transactionid);
            CheckResultResponse checkResultResponse = client.checkResultWithOptions(checkResultRequest, runtime);
            return checkResultResponse;
        }catch (Exception error) {
            logger.error("checkResult",error);
        }
        return null;

    }
}
