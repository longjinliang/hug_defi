package com.ruoyi.web.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ruoyi.business.model.CAddressVerified;
import com.ruoyi.business.model.CUser;
import com.ruoyi.business.service.AddressVerifiedService;
import com.ruoyi.business.service.AliyunKycService;
import com.ruoyi.business.service.UserService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户接口
 * 
 * @author ruoyi
 */
@RestController
public class AddressVerifiedController extends BaseController
{


    @Autowired
    AddressVerifiedService addressVerifiedService;
    @Autowired
    ISysConfigService sysConfigService;
    @Autowired
    AliyunKycService aliyunKycService;
    @Autowired
    UserService userService;





    /**
     * 认证情况
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/verified/info")
    public AjaxResult getVerifiedInfoV1(HttpServletResponse response, HttpServletRequest request, @RequestBody JSONObject param)
    {
        String address=getUserAddress();
        String transactionId=param.getString("transactionId");

        CAddressVerified addressVerified= addressVerifiedService.queryVerifiedStatus(address,transactionId);

        int status=0;
        if(addressVerified!=null){
            status=addressVerified.getStatus();
        }

        return success(status);
    }



//    /**
//     * 认证
//     *
//     * @param
//     * @return 结果
//     */
//    @PostMapping("/verified")
//    public AjaxResult verified(HttpServletRequest request, @RequestBody CAddressVerified addressVerified)
//    {
//        String address = getUserAddress();
//        String identityType = addressVerified.getIdentityType();
//        if(StringUtils.isEmpty(identityType)){
//            return error("证件类型不能为空");
//        }
//        if(StringUtils.isEmpty(addressVerified.getActualName())
//                || StringUtils.isEmpty(addressVerified.getIdentityNumber())
//                || StringUtils.isEmpty(addressVerified.getPage1())
//                || StringUtils.isEmpty(addressVerified.getPage3())){
//            return error("填写信息不完整");
//        }
//        if(!addressVerified.getIdentityType().equals("passport")&&StringUtils.isEmpty(addressVerified.getPage2())){
//            return error("填写信息不完整");
//        }
//
//        addressVerifiedService.verified(address,
//                addressVerified.getActualName(),
//                addressVerified.getIdentityNumber(),
//                addressVerified.getPage1(),
//                addressVerified.getPage2(),
//                addressVerified.getPage3(),
//                addressVerified.getIdentityType());
//
//        return success();
//    }


    /**
     * 认证
     *
     * @param param
     * @return 结果
     */
    @PostMapping("/verified/params")
    public AjaxResult getVerifiedParams(HttpServletRequest request, @RequestBody JSONObject param)
    {
        String address = getUserAddress();
        CUser user = userService.getByAddress(address);
        String identityType = param.getString("identityType");
        String identityNumber = param.getString("identityNumber").toLowerCase();
        String actualName=param.getString("actualName");

        JSONObject metainfo = param.getJSONObject("metainfo");
        if(StringUtils.isEmpty(identityType)){
            return error(MessageUtils.message("证件类型不能为空","verified.params.type.notnull"));
        }

        if(StringUtils.isEmpty(identityNumber)
                || StringUtils.isEmpty(actualName)){
            return error(MessageUtils.message("填写信息不完整","verified.params.name.notnull"));
        }

        List<CAddressVerified> list=addressVerifiedService.getByIdentityNumber(identityNumber.toLowerCase());

        CAddressVerified addressVerified1 = list.stream().filter(item -> !item.getAddress().equalsIgnoreCase(address)).findFirst().orElse(null);
        if(addressVerified1 !=null){
            return error(MessageUtils.message("该证件已实名认证","verified.params.num.exist"));
        }

        JSONObject verifiedParams = addressVerifiedService.getVerifiedParams(address, identityType, metainfo,identityNumber,actualName);

        return success(verifiedParams);
    }


    /**
     * AWS S3 上传文件
     *
     * @return
     */
    @PostMapping("/aws/upload")
    public AjaxResult s3Upload(@RequestParam("file") MultipartFile file) throws Exception {
        String str = sysConfigService.selectConfigByKey("AWS_S3_CONFIG");
        if(StringUtils.isEmpty(str)){
            return AjaxResult.error("未查询到配置信息");
        }
        JSONObject jsonObject = JSON.parseObject(str);
        String AWS_ACCESS_KEY = jsonObject.getString("AWS_ACCESS_KEY");
        String AWS_SECRET_KEY = jsonObject.getString("AWS_SECRET_KEY");
        String BUCKET_NAME = jsonObject.getString("BUCKET_NAME");
        String REGIONS_NAME=jsonObject.getString("REGIONS_NAME");
        Regions regions =Regions.fromName(REGIONS_NAME);

        AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion(regions)
                .withCredentials(awsCredentialsProvider)
                .build();

        // 获取文件名
        String fileName = file.getOriginalFilename();
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());

        String name = fileName.substring(0, fileName.lastIndexOf("."));
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        String dateStr = DateUtils.parseDateToStr("yyyyMMdd", new Date());
        String prefixFileName = dateStr+"/"+name + "_" + String.valueOf(System.currentTimeMillis()).substring(6)  + fileType;


        // 上传文件到 S3
        s3Client.putObject(new PutObjectRequest(BUCKET_NAME, prefixFileName, convFile));
        convFile.delete();

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 1;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(BUCKET_NAME, prefixFileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

        // 文件访问地址
        StringBuilder urlImage = new StringBuilder();
        urlImage.append(url.getProtocol()).append("://").append(url.getHost()).
                append(URLDecoder.decode(url.getPath(), "UTF-8"));
        // 预签名put地址
        StringBuilder preUrl = new StringBuilder();
        preUrl.append(url.getProtocol()).append("://").append(url.getHost()).
                append(URLDecoder.decode(url.getFile(), "UTF-8"));

        Map<String, Object> map = new HashMap<>();
//        map.put("preUrl", preUrl);
        map.put("fileUrl", urlImage);
        return AjaxResult.success(map);
    }

    /**
     * 检测是否重复
     *
     * @param
     * @return 结果
     */
    @PostMapping("/verified/check")
    public AjaxResult checkVerified(HttpServletRequest request, @RequestBody CAddressVerified addressVerified)
    {
        String address = getUserAddress();
        String identityType = addressVerified.getIdentityType();
        if(StringUtils.isEmpty(identityType)){
            return error("证件类型不能为空");
        }
        if(StringUtils.isEmpty(addressVerified.getActualName())
                || StringUtils.isEmpty(addressVerified.getIdentityNumber())){
            return error("填写信息不完整");
        }

        List<CAddressVerified> list=addressVerifiedService.getByIdentityNumber(addressVerified.getIdentityNumber().toLowerCase());

        CAddressVerified addressVerified1 = list.stream().filter(item -> item.getAddress().equalsIgnoreCase(address)).findFirst().orElse(null);
        if(addressVerified1 !=null){
            return error("该证件已实名认证");
        }



        return success(true);
    }




}
