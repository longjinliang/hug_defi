package com.ruoyi.web.controller.business;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ruoyi.business.model.CReward;
import com.ruoyi.business.service.RewardService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 菜单信息
 *
 * @author ruoyi
 */
@RestController
public class RewardController extends BaseController
{

    @Autowired
    private RewardService rewardService;


    /**
     * 列表
     */
    @PostMapping("/reward/list")
    public AjaxResult getBackRewardList(@RequestBody JSONObject params)
    {
        String address=params.getString("address");
        String type=params.getString("type");
        Date startTime=params.getDate("startTime");
        Date endTime=params.getDate("endTime");
        Integer pageNum=params.getIntValue("pageNum",1);
        Integer pageSize=params.getIntValue("pageSize",10);
        PageHelper.startPage(pageNum,pageSize);

        List<CReward> list=rewardService.getBackRewardList(address,startTime,endTime,type);
        TableDataInfo dataTable = getDataTable(list);
        JSONObject result=new JSONObject();
        result.put("total",dataTable.getTotal());
        result.put("list",dataTable.getRows());

        List<JSONObject> sumInfo=rewardService.getBackRewardListSum(address,startTime,endTime,type);
        result.put("sumInfo",sumInfo);

        return success(result);
    }









}