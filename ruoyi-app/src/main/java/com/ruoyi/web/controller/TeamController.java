package com.ruoyi.web.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.business.mapper.CTeamConfigMapper;
import com.ruoyi.business.model.CBalance;
import com.ruoyi.business.model.CFeeOrder;
import com.ruoyi.business.model.CUser;
import com.ruoyi.business.model.CUserStatics;
import com.ruoyi.business.service.BalanceService;
import com.ruoyi.business.service.PayFeeService;
import com.ruoyi.business.service.UserService;
import com.ruoyi.business.service.UserStaticsService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TeamController extends BaseController
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
    @Autowired
    CTeamConfigMapper teamConfigMapper;




    /**
     * 查询团队人数
     *
     * @param param
     * @return 结果
     */
    @PostMapping("/team/info")
    public AjaxResult getTeamInfo(HttpServletRequest request,@RequestBody JSONObject param)
    {
        String address=getUserAddress();
        CUserStatics userStatics = userStaticsService.getByAddress(address);
        CUser user = userService.getByAddress(address);
        int teamCount = userService.getTeamCount(user.getUserTree());
        int directCount = userService.getDirectCount(address);
        int teamPayCount=payFeeService.getTeamPayCount(user.getUserTree());
        int teamEffectCount=payFeeService.getTeamEffectCount(user.getUserTree());


        JSONObject obj=new JSONObject();
        obj.put("teamCount",teamCount);
        obj.put("directCount",directCount);
        obj.put("teamPayCount",teamPayCount);
        obj.put("teamEffectCount",teamEffectCount);
        obj.put("parentAddress",user.getParentAddress());
        obj.put("teamTotalCount",userStatics.getTotalTeamCount());
        return success(obj);
    }



//    /**
//     * 查询等级奖励进度列表
//     *
//     * @param param
//     * @return 结果
//     */
//    @PostMapping("/team/reward/list")
//    public AjaxResult getTeamRewardList(HttpServletRequest request,@RequestBody JSONObject param)
//    {
//        String address=getUserAddress();
//        CBalance balance = balanceService.getByAddress("GALE", address, "reward");
//        JSONObject obj=new JSONObject();
//        obj.put("galeAmount",balance.getAmount());
//
//        CUserStatics userStatics = userStaticsService.getByAddress(address);
//        Integer totalTeamCount = userStatics.getTotalTeamCount();
//
//        List<JSONObject> list=teamConfigMapper.getList(address);
//        list.forEach(item->{
//            item.put("totalTeamCount",totalTeamCount);
//            int status=0;
//            if(item.getInteger("isTake") !=null){
//                status=1;
//                if(item.getInteger("isTake").intValue()==0){
//                    status=2;
//                }else{
//                    status=3;
//                }
//            }
//            item.put("status",status);
//
//            item.put("totalTeamCount",totalTeamCount);
//            item.put("processRatio",new BigDecimal(totalTeamCount).divide(item.getBigDecimal("teamCount"),6,BigDecimal.ROUND_HALF_UP));
//            if(item.getBigDecimal("processRatio").compareTo(BigDecimal.ONE)>0){
//                item.put("processRatio",BigDecimal.ONE);
//            }
//        });
//        obj.put("list",list);
//
//        return success(obj);
//    }


    /**
     * 获取直推列表
     *
     * @param param
     * @return 结果
     */
    @PostMapping("/user/direct/list")
    public AjaxResult getUserDirectList(HttpServletRequest request,@RequestBody JSONObject param)
    {
        String address=getUserAddress();
        int pageNum = param.getIntValue("pageNum", 1);
        int pageSize = param.getIntValue("pageSize", 10);
        PageHelper.startPage(pageNum,pageSize);

        List<JSONObject> list=userStaticsService.getUserDirectList(address);
        PageInfo<JSONObject> pageInfo = new PageInfo<>(list);
        JSONObject obj=new JSONObject();
        obj.put("total",pageInfo.getTotal());
        obj.put("list",pageInfo.getList());

        return success(obj);
    }




}
