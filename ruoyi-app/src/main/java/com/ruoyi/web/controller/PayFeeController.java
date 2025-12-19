package com.ruoyi.web.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ruoyi.business.model.*;
import com.ruoyi.business.service.*;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户接口
 * 
 * @author ruoyi
 */
@RestController
public class PayFeeController extends BaseController
{
    @Autowired
    private UserService userService;
    @Autowired
    private PayFeeService payFeeService;

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
    @PostMapping("/pay/fee/sign")
    public AjaxResult getPayFeeSign(HttpServletRequest request)
    {
        String address=getUserAddress();
        logger.info("getPayFeeSign方法，address:{},ip:{}",address, IpUtils.getIpAddr());

        CUser user = userService.getByAddress(address);
        if(StringUtils.isEmpty(user.getParentAddress())){
            return AjaxResult.error(511,MessageUtils.message("请绑定推荐人地址再操作","bind.inviter.address"));
        }

        String str = sysConfigService.selectNoCacheConfigByKey("pay_fee_config");
        JSONObject config = JSON.parseObject(str);

        String whites = sysConfigService.selectNoCacheConfigByKey("whites").toLowerCase();

        if(config.getDate("startTime").compareTo(new Date())>0&& !whites.contains(address)){
            return error(MessageUtils.message("空投暂未开始","airdrop.notstart"));
        }

        if (StringUtils.isNull(user))
        {
            logger.info("登录用户：{} 不存在.", address);
            throw new ServiceException(MessageUtils.message("user.not.exists"));
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            logger.info("登录用户：{} 已被删除.", address);
            throw new ServiceException(MessageUtils.message("user.password.delete"));
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            logger.info("登录用户：{} 已被停用.", address);
            throw new ServiceException(MessageUtils.message("user.blocked"));
        }


        JSONObject obj=payFeeService.getPayFeeSign(address,null);

        return success(obj);
    }



    /**
     * 获取订单列表
     *
     * @param param
     * @return 结果
     */
    @PostMapping("/pay/fee/list")
    public AjaxResult getPayFeeList(HttpServletRequest request,@RequestBody JSONObject param)
    {
        String address=getUserAddress();

        Object info = redisCache.getCacheObject("getPayFeeList");
        if(info !=null){
            return success(info);
        }else{
            int pageNum = param.getIntValue("pageNum", 1);
            int pageSize = param.getIntValue("pageSize", 10);
            List<JSONObject> list=payFeeService.getPayFeeList(pageNum,pageSize,null);
            PageInfo<JSONObject> pageInfo = new PageInfo<>(list);
            JSONObject obj=new JSONObject();
            obj.put("total",pageInfo.getTotal());
            obj.put("list",pageInfo.getList());
            redisCache.setCacheObject("getPayFeeList",obj,3, TimeUnit.SECONDS);
            return success(obj);
        }

    }

    /**
     * 获取订单列表
     *
     * @param param
     * @return 结果
     */
    @PostMapping("/pay/fee/my/list")
    public AjaxResult getPayFeeMyList(HttpServletRequest request,@RequestBody JSONObject param)
    {
        String address=getUserAddress();

        int pageNum = param.getIntValue("pageNum", 1);
        int pageSize = param.getIntValue("pageSize", 10);
        List<JSONObject> list=payFeeService.getPayFeeList(pageNum,pageSize,address);
        PageInfo<JSONObject> pageInfo = new PageInfo<>(list);
        JSONObject obj=new JSONObject();
        obj.put("total",pageInfo.getTotal());
        obj.put("list",pageInfo.getList());
        return success(obj);

    }


    /**
     * 获取统计页信息
     *
     * @param param
     * @return 结果
     */
    @PostMapping("/pay/fee/info")
    public AjaxResult getPayFeeInfo(HttpServletRequest request,@RequestBody JSONObject param)
    {
        String address=getUserAddress();


        String aa =redisCache.getCacheObject("getPayFeeInfo");
        JSONObject obj=JSON.parseObject(aa);

        if(obj ==null){

             obj=new JSONObject();

            //参与用户数
            Integer userCount=payFeeService.getUserCount();

            //交互次数
            Integer payCount = payFeeService.getPayCount();


            //已空投数量
            BigDecimal totalTokenAmount=payFeeService.getTotalTokenAmount();
            if(totalTokenAmount==null){
                totalTokenAmount=new BigDecimal(0);
            }

            String str = sysConfigService.selectNoCacheConfigByKey("pay_fee_config");
            JSONObject config = JSON.parseObject(str);
            BigDecimal tta = config.getBigDecimal("totalTokenAmount");
            if(totalTokenAmount.compareTo(tta)>0){
                totalTokenAmount=tta;
            }

            BigDecimal subAmount=tta.subtract(totalTokenAmount);



            obj.put("userCount",userCount);
            obj.put("payCount",payCount);
            obj.put("totalTokenAmount",totalTokenAmount);
            obj.put("subAmount",subAmount);

            redisCache.setCacheObject("getPayFeeInfo",JSON.toJSONString(obj),20, TimeUnit.SECONDS);

        }

        int userPayCount = payFeeService.getUserPayCount(address);

        String s = sysConfigService.selectNoCacheConfigByKey("pay_fee_config");
        JSONObject config = JSON.parseObject(s);
        obj.put("startTime",config.getDate("startTime"));

        Date canBuyTime=new Date();

        int todayCount=payFeeService.getTodayCount(address);

        BigDecimal nextAmount=new BigDecimal(todayCount+1).multiply(new BigDecimal(5));
        if(todayCount>=4){
            nextAmount=BigDecimal.ZERO;
        }

        int roundMinutes = config.getIntValue("roundMinutes");

        if(todayCount>0){
            CFeeOrder todayOrder = payFeeService.getTodayOrder(address);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(todayOrder.getCreateTime());
            calendar.add(Calendar.MINUTE,roundMinutes);

            canBuyTime=calendar.getTime();
            if(canBuyTime.getDate() != new Date().getDate()){
                Calendar cd=Calendar.getInstance();
                cd.setTime(new Date());
                cd.add(Calendar.DATE,1);
                cd.set(Calendar.HOUR_OF_DAY,0);
                cd.set(Calendar.MINUTE,0);
                cd.set(Calendar.SECOND,0);
                canBuyTime=cd.getTime();
                nextAmount=new BigDecimal(5);
            }
        }


        obj.put("canBuyTime",canBuyTime);
        obj.put("nowTime",new Date());
        obj.put("userPayCount",userPayCount);
        obj.put("nextAmount",nextAmount);
        obj.put("userTodayCount",todayCount);
        obj.put("todayRemainCount",4-todayCount<0?0:4-todayCount);

        return success(obj);
    }

    /**
     * 测试IP地址
     *
     * @param param
     * @return 结果
     */
    @GetMapping("/test/ip")
    @Anonymous
    public AjaxResult testIp(HttpServletRequest request)
    {

        return success(IpUtils.getIpAddr(request));
    }



}
