package com.ruoyi.web.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.business.model.CBalance;
import com.ruoyi.business.model.CReward;
import com.ruoyi.business.service.BalanceService;
import com.ruoyi.business.service.RewardService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户接口
 * 
 * @author ruoyi
 */
@RestController
public class RewardController extends BaseController
{


    @Autowired
    RewardService rewardService;

    @Autowired
    BalanceService balanceService;




    /**
     * 查询奖励记录
     *
     * @param param
     * @return 结果
     */
    @PostMapping("/reward/list")
    public AjaxResult getRewardList(HttpServletRequest request,@RequestBody JSONObject param)
    {
        String address=getUserAddress();
        int pageNum = param.getIntValue("pageNum", 1);
        int pageSize = param.getIntValue("pageSize", 10);
        int type = param.getIntValue("type");
        PageHelper.startPage(pageNum, pageSize);
        if(type==1){
            List<CReward> list=rewardService.getRewardList(address, Arrays.asList("team"));
            List<JSONObject> collect = list.stream().map(item -> {
                JSONObject obj = new JSONObject();
                obj.put("createTime", item.getCreateTime());
                obj.put("rewardType", item.getRewardType());
                obj.put("coin", item.getCoin());
                obj.put("teamCount",item.getTeamCount());
                obj.put("rewardAmount", item.getRewardAmount());
                return obj;
            }).collect(Collectors.toList());
            PageInfo<CReward> pageInfo = new PageInfo<>(list);
            JSONObject result=new JSONObject();
            result.put("total",pageInfo.getTotal());
            result.put("list",collect);
            return success(result);

        }else{
            List<CReward> list=rewardService.getRewardList(address, Arrays.asList("direct_buy_reward","node_buy_reward"));

            List<JSONObject> collect = list.stream().map(item -> {
                JSONObject obj = new JSONObject();
                obj.put("createTime", item.getCreateTime());
                obj.put("rewardType", item.getRewardType());
                obj.put("coin", item.getCoin());
                obj.put("orderAddress",item.getOrderAddress());
                obj.put("teamCount",item.getTeamCount());
                obj.put("rewardAmount", item.getRewardAmount());
                return obj;
            }).collect(Collectors.toList());
            PageInfo<CReward> pageInfo = new PageInfo<>(list);
            JSONObject result=new JSONObject();
            result.put("total",pageInfo.getTotal());
            result.put("list",collect);
            return success(result);
        }
    }


    /**
     * 查询奖励数据
     *
     * @param param
     * @return 结果
     */
    @PostMapping("/reward/sum/info")
    public AjaxResult getRewardSumInfo(HttpServletRequest request,@RequestBody JSONObject param)
    {
        String address=getUserAddress();
        
        //已获得铸造佣金
        BigDecimal airdropRewardAmount=rewardService.getRewardSumByTypes(address,Arrays.asList("team"));
        
        //已获得认购佣金
        BigDecimal buyRewardAmount=rewardService.getRewardSumByTypes(address,Arrays.asList("node_buy_reward","direct_buy_reward"));

        //可提取佣金
        CBalance balance = balanceService.getByAddress("USDT",address,"reward");

        JSONObject result=new JSONObject();
        result.put("airdropRewardAmount",airdropRewardAmount);
        result.put("buyRewardAmount",buyRewardAmount);
        result.put("balanceAmount",balance.getAmount());
        return success(result);



    }






}
