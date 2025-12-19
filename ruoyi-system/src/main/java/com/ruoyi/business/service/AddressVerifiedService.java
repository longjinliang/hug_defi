package com.ruoyi.business.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aliyun.cloudauth_intl20220809.models.CheckResultResponse;
import com.aliyun.cloudauth_intl20220809.models.InitializeResponse;
import com.aliyun.cloudauth_intl20220809.models.InitializeResponseBody;
import com.ruoyi.business.mapper.CAddressVerifiedMapper;
import com.ruoyi.business.mapper.CKycCodeMapper;
import com.ruoyi.business.mapper.CKycRecordMapper;
import com.ruoyi.business.model.CAddressVerified;
import com.ruoyi.business.model.CKycCode;
import com.ruoyi.business.model.CKycRecord;
import com.ruoyi.business.model.CUser;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AddressVerifiedService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    CAddressVerifiedMapper addressVerifiedMapper;

    @Autowired
    AliyunKycService aliyunKycService;
    @Autowired
    CKycRecordMapper kycRecordMapper;
    @Autowired
    CKycCodeMapper kycCodeMapper;


    public CAddressVerified getByAddress(String address) {
        Example example=new Example(CAddressVerified.class);
        example.createCriteria().andEqualTo("address", address);
        return addressVerifiedMapper.selectOneByExample(example);
    }

    public void verified(String address, String name, String number, String page1, String page2, String page3, String identityType) {
        CAddressVerified addressVerified = getByAddress(address);
        if(addressVerified !=null && addressVerified.getStatus().intValue() !=3){
            throw new RuntimeException("认证资料正在审核中，请耐心等待!");
        }

        Example example=new Example(CAddressVerified.class);
        example.createCriteria().andEqualTo("identityNumber", number.toLowerCase())
                .andNotEqualTo("address", address);
        int count = addressVerifiedMapper.selectCountByExample(example);
        if(count > 0){
            throw new RuntimeException("证件号码已存在!");
        }


        if(addressVerified==null){
            addressVerified=new CAddressVerified();
        }

        addressVerified.setAddress(address);
        addressVerified.setActualName(name);
        addressVerified.setIdentityNumber(number.toLowerCase());
        addressVerified.setIdentityType(identityType);
        addressVerified.setPage1(page1);
        addressVerified.setPage2(page2);
        addressVerified.setPage3(page3);
        addressVerified.setStatus(1);
        addressVerified.setApplyTime(new Date());
        addressVerified.setUpdateTime(new Date());
        if(addressVerified.getId()!=null){
            addressVerifiedMapper.updateByPrimaryKeySelective(addressVerified);
        }else{
            addressVerified.setCreateTime(new Date());
            addressVerifiedMapper.insertSelective(addressVerified);
        }
    }

    public List<CAddressVerified> getBackList(String address, Integer status, String idNumber, String name) {
        Example example=new Example(CAddressVerified.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(address)){
            criteria.andEqualTo("address", address);
        }
        if(status !=null){
            criteria.andEqualTo("status",status);
        }
        if(StringUtils.isNotEmpty(idNumber)){
            criteria.andEqualTo("identityNumber",idNumber);
        }
        if(StringUtils.isNotEmpty(name)){
            criteria.andEqualTo("actualName",name);
        }
        example.orderBy("id").desc();
        return addressVerifiedMapper.selectByExample(example);
    }

    public void audit(long id, int status, String suggest) {
        CAddressVerified addressVerified = addressVerifiedMapper.selectByPrimaryKey(id);
        addressVerified.setStatus(status);
        addressVerified.setAuditTime(new Date());
        addressVerified.setUpdateTime(new Date());
        addressVerified.setSuggest(suggest);
        addressVerifiedMapper.updateByPrimaryKeySelective(addressVerified);
    }

    public CAddressVerified queryVerifiedStatus(String address, String transactionId) {
        CAddressVerified addressVerified = getByAddress(address);
        if(StringUtils.isEmpty(transactionId)){
            return addressVerified;
        }
        if(addressVerified !=null && addressVerified.getStatus().intValue() ==2){
            return addressVerified;
        }

        Example example=new Example(CKycRecord.class);
        example.createCriteria().andEqualTo("address", address)
        .andEqualTo("transactionid", transactionId);
        CKycRecord cKycRecord = kycRecordMapper.selectOneByExample(example);
        if(cKycRecord==null){
            throw new RuntimeException("transactionId参数有误");

        }

        CheckResultResponse checkResultResponse = aliyunKycService.checkResult(cKycRecord.getMerchantbizid(), cKycRecord.getTransactionid());
        if(checkResultResponse !=null && checkResultResponse.getStatusCode().intValue()==200){
            cKycRecord.setKycinfo(JSON.toJSONString(checkResultResponse));
            if(checkResultResponse.body.code.equalsIgnoreCase("Success")){
                if(checkResultResponse.body.getResult().getSubCode().equals("200")){

                    CKycCode kycCode = getKycCode(cKycRecord.getIdentityType());

                    if(kycCode==null|| kycCode.getIsOut().intValue()==0){
                        String extIdInfo = checkResultResponse.body.getResult().getExtIdInfo();
                        JSONObject jsonObject = JSON.parseObject(extIdInfo);

                        String idImage=jsonObject.getString("idImage");
                        JSONObject ocrIdInfo = jsonObject.getJSONObject("ocrIdInfo");
                        String name = ocrIdInfo.getString("name");
                        String idNumber=ocrIdInfo.getString("idNumber");
                        JSONObject extFaceInfo = JSON.parseObject(checkResultResponse.body.getResult().getExtFaceInfo());
                        String faceImg=extFaceInfo.getString("faceImg");

                        if(idNumber.equalsIgnoreCase(cKycRecord.getIdentityNumber())){
                            cKycRecord.setStatus(2);
                            addressVerified.setStatus(2);
                            addressVerified.setIdentityNumber(idNumber);
                            addressVerified.setActualName(name);
                            addressVerified.setIdentityType(cKycRecord.getIdentityType());
                            addressVerified.setAuditTime(new Date());
                            addressVerified.setUpdateTime(new Date());
                            addressVerified.setPage1(idImage);
                            addressVerified.setPage3(faceImg);
                            addressVerifiedMapper.updateByPrimaryKeySelective(addressVerified);
                        }
                    }else{
                        String extIdInfo = checkResultResponse.body.getResult().getExtIdInfo();
                        JSONObject extIdInfoData = JSON.parseObject(extIdInfo);
                        JSONObject jsonObject = extIdInfoData.getJSONObject("ocrIdInfoData").getJSONObject("01");

                        String idImage=jsonObject.getString("idImage");
                        JSONObject ocrIdInfo = jsonObject.getJSONObject("ocrIdInfo");
                        String surname = ocrIdInfo.getString("surname");
                        String given_names=ocrIdInfo.getString("given_names");
                        String name=surname+" "+given_names;
                        String idNumber=ocrIdInfo.getString("passport_number");
                        JSONObject extFaceInfo = JSON.parseObject(checkResultResponse.body.getResult().getExtFaceInfo());
                        String faceImg=extFaceInfo.getString("faceImg");

                        if(idNumber.equalsIgnoreCase(cKycRecord.getIdentityNumber())){
                            cKycRecord.setStatus(2);
                            addressVerified.setStatus(2);
                            addressVerified.setIdentityNumber(idNumber);
                            addressVerified.setActualName(name);
                            addressVerified.setIdentityType(cKycRecord.getIdentityType());
                            addressVerified.setAuditTime(new Date());
                            addressVerified.setUpdateTime(new Date());
                            addressVerified.setPage1(idImage);
                            addressVerified.setPage3(faceImg);
                            addressVerifiedMapper.updateByPrimaryKeySelective(addressVerified);
                        }
                    }
                }
            }
            cKycRecord.setUpdateTime(new Date());
            kycRecordMapper.updateByPrimaryKeySelective(cKycRecord);

        }

        addressVerified = getByAddress(address);


        return addressVerified;


    }

    public CKycCode getKycCode(String code) {
        Example example=new Example(CKycCode.class);
        example.createCriteria().andEqualTo("code", code);
        return kycCodeMapper.selectOneByExample(example);
    }

    public JSONObject getVerifiedParams(String address, String identityType, JSONObject metainfo, String identityNumber, String actualName) {

        CAddressVerified addressVerified = getByAddress(address);
        if(addressVerified!=null && addressVerified.getStatus().intValue() ==2){
            throw new RuntimeException(MessageUtils.message("您已KYC验证通过，不需要重复验证","verified.kyc.pass"));
        }

        CUser user = userService.getByAddress(address);
        String mechanId="asdfasdfasdfadsfasdfasdfasdfasdf";

        CKycCode kycCode = getKycCode(identityType);
        String productCoude="eKYC";
        if(kycCode.getIsOut().intValue()==1){
            productCoude="eKYC_PRO";
        }


        InitializeResponse initialize = aliyunKycService.initialize(productCoude,metainfo, identityType, user.getUserId().toString(),mechanId);
        if(initialize.getStatusCode().intValue()==200&& initialize.body.code.equalsIgnoreCase("Success")){
            InitializeResponseBody.InitializeResponseBodyResult result = initialize.body.getResult();

            String transactionId=result.getTransactionId();
            String requestId=initialize.body.requestId;
            String transactionUrl=result.getTransactionUrl();
            String protocol=result.getProtocol();

            if(addressVerified ==null){
                addressVerified=new CAddressVerified();
                addressVerified.setAddress(address);
                addressVerified.setApplyTime(new Date());
                addressVerified.setCreateTime(new Date());
                addressVerified.setUpdateTime(new Date());
                addressVerified.setStatus(3);
                addressVerifiedMapper.insertSelective(addressVerified);
            }
            CKycRecord record=new CKycRecord();
            record.setAddress(address);
            record.setActualName(actualName);
            record.setIdentityType(identityType);
            record.setIdentityNumber(identityNumber);
            record.setMerchantbizid(mechanId);
            record.setTransactionid(transactionId);
            record.setMerchantuserid(user.getUserId().toString());
            record.setRequestid(requestId);
            record.setStatus(3);
            record.setUpdateTime(new Date());
            record.setCreateTime(new Date());
            kycRecordMapper.insertSelective(record);

            JSONObject obj=new JSONObject();
            obj.put("transactionId",transactionId);
            obj.put("transactionUrl",transactionUrl);
            obj.put("protocol",protocol);
            return obj;

        }
        return null;
    }

    public List<CAddressVerified> getByIdentityNumber(String number) {

        Example example=new Example(CAddressVerified.class);
        example.createCriteria().andEqualTo("identityNumber", number.toLowerCase());
        return addressVerifiedMapper.selectByExample(example);
    }

    public void updateVerifed(CAddressVerified addressVerified) {
        addressVerifiedMapper.updateByPrimaryKeySelective(addressVerified);
    }

    public CKycRecord getLastKycRecord(String address) {
        return kycRecordMapper.getLastKycRecord(address);
    }

    public List<CKycCode> getVerifiedCodes() {
        Example example=new Example(CKycCode.class);
        example.createCriteria().andEqualTo("enabled",1);
        return kycCodeMapper.selectByExample(example);

    }
}
