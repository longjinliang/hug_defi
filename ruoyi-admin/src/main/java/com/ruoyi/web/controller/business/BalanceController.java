package com.ruoyi.web.controller.business;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ruoyi.business.model.CBalance;
import com.ruoyi.business.service.BalanceService;
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
public class BalanceController extends BaseController
{
    @Autowired
    private BalanceService balanceService;

    /**
     * 账户列表
     */
    @PostMapping("/balance/list")
    public AjaxResult getBalanceList(@RequestBody JSONObject params)
    {
        String coin=params.getString("coin");

        String address=params.getString("address");
        String balanceType=params.getString("balanceType");
        Integer pageNum=params.getIntValue("pageNum",1);
        Integer pageSize=params.getIntValue("pageSize",10);
        PageHelper.startPage(pageNum,pageSize);
        List<CBalance> list=balanceService.getBackBalanceList(address,coin,balanceType);

        List<JSONObject> sumInfo=balanceService.getBackBalanceListSum(address,coin,balanceType);

        TableDataInfo dataTable = getDataTable(list);

        JSONObject result=new JSONObject();
        result.put("total",dataTable.getTotal());
        result.put("list",dataTable.getRows());

        result.put("sumInfo",sumInfo);
        return success(result);

    }

    /**
     * 资金流水
     */
    @PostMapping("/balance/log/list")
    public TableDataInfo getBackBalanceLogList(@RequestBody JSONObject params)
    {
        String coin=params.getString("coin");
        String address=params.getString("address");
        Date startTime=params.getDate("startTime");
        Date endTime=params.getDate("endTime");
        String balanceType=params.getString("balanceType");
        Integer pageNum=params.getIntValue("pageNum",1);
        Integer pageSize=params.getIntValue("pageSize",10);
        PageHelper.startPage(pageNum,pageSize);

        List<JSONObject> list=balanceService.getBackBalanceLogList(address,coin,startTime,endTime,balanceType);
        return getDataTable(list);
    }




}