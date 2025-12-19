package com.ruoyi.web.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.business.model.CBuyStage;
import com.ruoyi.business.model.CUserStatics;
import com.ruoyi.business.service.BalanceService;
import com.ruoyi.business.service.BuyService;
import com.ruoyi.business.service.UserService;
import com.ruoyi.business.service.UserStaticsService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户接口
 * 
 * @author ruoyi
 */
@RestController
public class BuyController extends BaseController
{
    @Autowired
    private UserService userService;
    @Autowired
    private BuyService buyService;

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    BalanceService balanceService;


    @Autowired
    RedisCache redisCache;

    @Autowired
    UserStaticsService userStaticsService;






    /**
     * 获取支付签名
     *
     * @param
     * @return 结果
     */
    @PostMapping("/buy/sign")
    public AjaxResult getBuySign(HttpServletRequest request,@RequestBody JSONObject params)
    {
        String address=getUserAddress();

        BigDecimal amount = params.getBigDecimal("amount");
        if(amount.compareTo(BigDecimal.ZERO)<=0){
            return error(MessageUtils.message("参数错误","params.error"));
        }

        String s = sysConfigService.selectNoCacheConfigByKey("buy_config");
        JSONObject config = JSON.parseObject(s, JSONObject.class);
        String whites = sysConfigService.selectNoCacheConfigByKey("whites").toLowerCase();

        if(config.getDate("startTime").compareTo(new Date())>0 && !whites.contains(address)){
            return error(MessageUtils.message("认购暂未开始","buy.notstart"));
        }

        JSONObject obj=buyService.getBuySign(address,amount);

        return success(obj);
    }



    /**
     * 获取购买记录列表
     *
     * @param param
     * @return 结果
     */
    @PostMapping("/buy/list")
    public AjaxResult getBuyList(HttpServletRequest request,@RequestBody JSONObject param)
    {
        String address=getUserAddress();

        int pageNum = param.getIntValue("pageNum", 1);
        int pageSize = param.getIntValue("pageSize", 10);

        PageHelper.startPage(pageNum,pageSize);
//        List<JSONObject> list=buyService.getBuyList();
        List<JSONObject> list=buyService.getMyBuyList(address);
        PageInfo<JSONObject> pageInfo = new PageInfo<>(list);

        JSONObject obj=new JSONObject();
        obj.put("total",pageInfo.getTotal());
        obj.put("totalPages",pageInfo.getPages());
        obj.put("list",pageInfo.getList());
        return success(obj);

    }


    /**
     * 获取购买统计页信息
     *
     * @param param
     * @return 结果
     */
    @PostMapping("/buy/info")
    public AjaxResult getBuyInfo(HttpServletRequest request,@RequestBody JSONObject param)
    {
        String address=getUserAddress();

        //已认购
        BigDecimal tokenAmount=buyService.getMyBuyTokenAmount(address);
        if(tokenAmount==null){
            tokenAmount=BigDecimal.ZERO;
        }

        //距离节点还差多少usdt
        BigDecimal usdtAmount=buyService.getMyBuyUsdtAmount(address);
        if(usdtAmount==null){
            usdtAmount=BigDecimal.ZERO;
        }

        String s = sysConfigService.selectNoCacheConfigByKey("buy_config");
        JSONObject config = JSON.parseObject(s, JSONObject.class);
        BigDecimal nodeUsdtAmount = config.getBigDecimal("nodeUsdtAmount");
        BigDecimal userMaxUsdtAmount = config.getBigDecimal("userMaxUsdtAmount");
        BigDecimal firstMinUsdtAmount = config.getBigDecimal("firstMinUsdtAmount");
        BigDecimal minUsdtAmount = config.getBigDecimal("minUsdtAmount");

        BigDecimal userSubBuyAmount = userMaxUsdtAmount.subtract(usdtAmount);
        if(userSubBuyAmount.compareTo(BigDecimal.ZERO)<0){
            userSubBuyAmount=BigDecimal.ZERO;
        }


        BigDecimal nodeSubAmount=nodeUsdtAmount.subtract(usdtAmount);
        if(nodeSubAmount.compareTo(BigDecimal.ZERO)<=0){
            nodeSubAmount=BigDecimal.ZERO;
        }

        CUserStatics userStatics = userStaticsService.getByAddress(address);

        CBuyStage buyStage=buyService.getCurrentStage();
        if(buyStage.getBuyAmount().compareTo(buyStage.getTotalAmount())>0){
            buyStage.setBuyAmount(buyStage.getTotalAmount());
        }

        if(usdtAmount.compareTo(BigDecimal.ZERO)>0){
            buyStage.setMinUsdtAmount(minUsdtAmount);
        }else{
            buyStage.setMinUsdtAmount(firstMinUsdtAmount);
        }



        JSONObject obj=new JSONObject();
        obj.put("tokenAmount",tokenAmount);
        obj.put("usdtAmount",usdtAmount);
        obj.put("nodeSubAmount",nodeSubAmount);
        obj.put("nodeLevel",userStatics.getNodeLevel());
        obj.put("stageInfo",buyStage);
        obj.put("userSubBuyAmount",userSubBuyAmount);

        return success(obj);
    }




}
