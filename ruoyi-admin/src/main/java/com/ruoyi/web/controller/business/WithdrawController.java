package com.ruoyi.web.controller.business;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ruoyi.business.model.CWithdraw;
import com.ruoyi.business.service.WithdrawService;
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
public class WithdrawController extends BaseController
{
    @Autowired
    private WithdrawService withdrawService;

    /**
     * 提币列表
     */
    @PostMapping("/withdraw/list")
    public AjaxResult getWithdrawList(@RequestBody JSONObject params)
    {
        String address = params.getString("address");
        Date startTime=params.getDate("startTime");
        Date endTime=params.getDate("endTime");
        String coin = params.getString("coin");
        String type = params.getString("type");
        Integer status=params.getInteger("status");

        Integer pageNum=params.getIntValue("pageNum",1);
        Integer pageSize=params.getIntValue("pageSize",10);
        PageHelper.startPage(pageNum,pageSize);

        List<CWithdraw> list=withdrawService.getWithdrawList(address,startTime,endTime,coin,status,type);
        TableDataInfo dataTable = getDataTable(list);
        List<JSONObject> sumInfo=withdrawService.getWithdrawListSum(address,startTime,endTime,coin,status,type);
        JSONObject result=new JSONObject();
        result.put("total",dataTable.getTotal());
        result.put("list",dataTable.getRows());
        result.put("sumInfo",sumInfo);
        return success(result);
    }

//    /**
//     * 提币审核
//     */
//    @PostMapping("/withdraw/audit")
//    public AjaxResult auditWithdraw(@RequestBody JSONObject params)
//    {
//
//        long id=params.getLongValue("id");
//        int status=params.getIntValue("status");
//
//        withdrawService.auditWithdraw(id,status);
//        return success();
//    }





}