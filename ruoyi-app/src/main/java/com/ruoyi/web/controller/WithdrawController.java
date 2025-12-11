package com.ruoyi.web.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.business.model.CUser;
import com.ruoyi.business.model.CWithdraw;
import com.ruoyi.business.service.UserService;
import com.ruoyi.business.service.WithdrawService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.EthSign;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户接口
 * 
 * @author ruoyi
 */
@RestController
public class WithdrawController extends BaseController
{
    @Autowired
    private UserService userService;

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    ISysConfigService sysConfigService;
    @Autowired
    RedisCache redisCache;



    /**
     * 提取币
     *
     * @param param
     * @return 结果
     */
    @PostMapping("/withdraw/sign")
    public AjaxResult getWithdrawSign(HttpServletRequest request, @RequestBody JSONObject param)
    {
        String address = getUserAddress();
        Integer withdrawType=param.getInteger("withdrawType");

        if(withdrawType.intValue() !=1&& withdrawType.intValue() !=2 && withdrawType.intValue() !=2){
            return error(MessageUtils.message("参数错误","params.error"));
        }

        CUser user = userService.getByAddress(address);

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

        JSONObject obj=withdrawService.getWithdrawSign(address,withdrawType);

        return success(obj);
    }












}
