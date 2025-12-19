package com.ruoyi.web.controller.business;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ruoyi.business.model.CBuyOrder;
import com.ruoyi.business.model.CFeeOrder;
import com.ruoyi.business.service.BuyService;
import com.ruoyi.business.service.PayFeeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 菜单信息
 * 
 * @author ruoyi
 */
@RestController
public class BuyController extends BaseController
{

    @Autowired
    private BuyService buyService;


    /**
     * 认购记录列表
     */
    @PostMapping("/buy/order/list")
    public AjaxResult getBackBuyList(@RequestBody JSONObject params)
    {
        String address=params.getString("address");
        Date startTime=params.getDate("startTime");
        Date endTime=params.getDate("endTime");
        Integer pageNum=params.getIntValue("pageNum",1);
        Integer pageSize=params.getIntValue("pageSize",10);


        PageHelper.startPage(pageNum,pageSize);

        List<CBuyOrder> list=buyService.getBackBuyList(address,startTime,endTime);
        list.forEach(order->{
            if(order.getLastAmount().compareTo(BigDecimal.ZERO)==0){
                BigDecimal parentRewardAmount=buyService.getRewardAmountByOrderId(order.getId(),"direct_buy_reward");
                if(parentRewardAmount !=null){
                    order.setParentRewardAmount(parentRewardAmount);
                }
                BigDecimal nodeRewardAmount=buyService.getRewardAmountByOrderId(order.getId(),"node_buy_reward");
                if(nodeRewardAmount !=null){
                    order.setNodeRewardAmount(nodeRewardAmount);
                }
                order.setLastAmount(order.getUsdtAmount().subtract(order.getParentRewardAmount()).subtract(order.getNodeRewardAmount()));
                buyService.updateOrder(order);
            }
        });
        TableDataInfo dataTable = getDataTable(list);
        JSONObject result=new JSONObject();
        result.put("total",dataTable.getTotal());
        result.put("list",dataTable.getRows());

        JSONObject sumInfo=buyService.getBackBuyListSum(address,startTime,endTime);
        result.put("sumInfo",sumInfo);

        return success(result);
    }












}