package com.ruoyi.web.controller.business;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ruoyi.business.model.CUser;
import com.ruoyi.business.service.UserService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单信息
 * 
 * @author ruoyi
 */
@RestController
public class UserController extends BaseController
{
    @Autowired
    private UserService userService;


    /**
     * 用户列表
     */
    @PostMapping("/app/user/list")
    public TableDataInfo getBackUserList(@RequestBody JSONObject params)
    {
        String address=params.getString("address");
        if(StringUtils.isNotEmpty(address)) address=address.toLowerCase();
        String parentAddress=params.getString("parentAddress");
        if(StringUtils.isNotEmpty(parentAddress)) parentAddress=parentAddress.toLowerCase();

        Integer nodeLevel=params.getInteger("nodeLevel");

        Date startTime=params.getDate("startTime");
        Date endTime=params.getDate("endTime");
        Integer pageNum=params.getIntValue("pageNum",1);
        Integer pageSize=params.getIntValue("pageSize",10);
        PageHelper.startPage(pageNum,pageSize);
        List<JSONObject> list=userService.getBackUserList(address,parentAddress,startTime,endTime,nodeLevel);
        return getDataTable(list);

    }

    /**
     * 禁用用户
     */
    @PostMapping("/app/user/enable")
    public AjaxResult enableUser(@RequestBody JSONObject params)
    {
        Long uid = params.getLong("uid");
        String status = params.getString("status");
        CUser user = userService.getById(uid);
        user.setStatus(status);
        userService.updateUser(user);
        return success();
    }


    /**
     * 修改备注
     */
    @PostMapping("/app/user/remark")
    public AjaxResult updateUserRemark(@RequestBody JSONObject params)
    {
        String address = params.getString("address").toLowerCase();
        String remark=params.getString("remark");
        CUser user = userService.getByAddress(address);
        user.setRemark(remark);
        userService.updateUser(user);
        return success();
    }

    /**
     * 获取所有上级
     */
    @PostMapping("/app/user/tree")
    public AjaxResult getUserTree(@RequestBody JSONObject params)
    {
        String address = params.getString("address").toLowerCase();
        CUser user = userService.getByAddress(address);
        List<Long> list = Arrays.stream(user.getUserTree().split("-")).map(item -> Long.valueOf(item)).collect(Collectors.toList());

        List<CUser> lst=userService.getListByIds(list);
        List<JSONObject> collect = lst.stream().map(item -> {
            JSONObject obj = new JSONObject();
            obj.put("address", item.getAddress());
            obj.put("remark", item.getRemark());
            return obj;
        }).collect(Collectors.toList());

        return success(collect);
    }



}