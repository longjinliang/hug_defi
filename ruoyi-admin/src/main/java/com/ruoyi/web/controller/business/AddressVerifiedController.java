package com.ruoyi.web.controller.business;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ruoyi.business.model.CAddressVerified;
import com.ruoyi.business.service.AddressVerifiedService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实名认证
 * 
 * @author ruoyi
 */
@RestController
public class AddressVerifiedController extends BaseController
{
    @Autowired
    private AddressVerifiedService addressVerifiedService;

    /**
     * 认证列表
     */
    @PostMapping("/verified/list")
    public AjaxResult getBackList(@RequestBody JSONObject params)
    {
        String address = params.getString("address");
        if(StringUtils.isNotEmpty(address)){
            address=address.toLowerCase();
        }
        Integer status=params.getInteger("status");
        String idNumber=params.getString("idNumber");
        if(StringUtils.isNotEmpty(idNumber)){
            idNumber=idNumber.toLowerCase();
        }
        String name=params.getString("name");

        Integer pageNum=params.getIntValue("pageNum",1);
        Integer pageSize=params.getIntValue("pageSize",10);
        PageHelper.startPage(pageNum,pageSize);

        List<CAddressVerified> list=addressVerifiedService.getBackList(address,status,idNumber,name);
        TableDataInfo dataTable = getDataTable(list);
        JSONObject result=new JSONObject();
        result.put("total",dataTable.getTotal());
        result.put("list",dataTable.getRows());
        return success(result);
    }

    /**
     * 审核
     */
    @PostMapping("/verified/audit")
    public AjaxResult audit(@RequestBody JSONObject params)
    {

        long id=params.getLongValue("id");
        int status=params.getIntValue("status");
        String suggest=params.getString("suggest");


        addressVerifiedService.audit(id,status,suggest);
        return success();
    }





}