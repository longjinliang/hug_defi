package com.ruoyi.web.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.model.CUser;
import com.ruoyi.business.service.BalanceService;
import com.ruoyi.business.service.UserService;
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
import java.util.Date;

/**
 * 用户接口
 * 
 * @author ruoyi
 */
@RestController
public class UserController extends BaseController
{
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    ISysConfigService sysConfigService;
    @Autowired
    RedisCache redisCache;

    @Autowired
    BalanceService balanceService;



    /**
     * 登录方法
     *
     * @param param 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(HttpServletRequest request, @RequestBody JSONObject param)
    {
        String address = param.getString("address").toLowerCase();
        String signature=param.getString("signature");
        String message="Welcome to HUG!";


        boolean validate = EthSign.validate(signature, message, address);
        if(!validate){
            return error(MessageUtils.message("签名失败","login.validate.error"));
        }


        CUser user = userService.getByAddress(address.toLowerCase());
        if(user==null){
            return error(MessageUtils.message("用户不存在","login.user.notfound"));
//            user=userService.addUser(address.toLowerCase());
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

        SysUser sysUser=new SysUser();
        sysUser.setUserId(user.getUserId());
        sysUser.setUserName(user.getUserName());


        LoginUser loginUser=new LoginUser(user.getUserId(), user.getAddress(),sysUser);
        String token = tokenService.createToken(loginUser);
        JSONObject obj=new JSONObject();
        obj.put("token",token);


        AsyncManager.me().execute(AsyncFactory.recordLogininfor(address, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        user.setLoginIp(IpUtils.getIpAddr());
        user.setLoginDate(new Date());
        userService.updateUser(user);

        return success(obj);
    }


    /**
     * 用户绑定上级
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/user/bind/parent")
    @Anonymous
    public AjaxResult bindParent(HttpServletResponse response, HttpServletRequest request, @RequestBody JSONObject param)
    {
        String address=param.getString("address").toLowerCase();
        String parentAddress=param.getString("parentAddress").toLowerCase();
        String message="Bind parent address!";
        String signature=param.getString("signature");

        CUser user = userService.getByAddress(address);
        if(user ==null){
            if(StringUtils.isEmpty(parentAddress)){
                return AjaxResult.error(101,MessageUtils.message("推荐人地址不能为空","bind.parent.address.empty"));
            }
            if(address.equalsIgnoreCase(param.getString("parentAddress"))){
                return AjaxResult.error(101,MessageUtils.message("推荐人地址错误","bind.inviter.address.error"));
            }

            if(StringUtils.isEmpty(message)|| StringUtils.isEmpty(signature)){
                return AjaxResult.error(MessageUtils.message("参数错误","params.error"));
            }
            boolean validate = EthSign.validate(signature, message, address);
            if(!validate){
                return AjaxResult.error(MessageUtils.message("签名失败","login.validate.error"));
            }

            CUser parent = userService
                    .getByAddress(parentAddress);
            if(parent==null || StringUtils.isEmpty(parent.getParentAddress())){
                return AjaxResult.error(101,MessageUtils.message("推荐人地址不存在","bind.inviter.notfound"));
            }

            user=userService.bindParent(address,parentAddress);


        }else{
            return AjaxResult.error(101,MessageUtils.message("参数错误","params.error"));
        }

        SysUser sysUser=new SysUser();
        sysUser.setUserId(user.getUserId());
        sysUser.setUserName(user.getUserName());

        LoginUser loginUser=new LoginUser(user.getUserId(), user.getAddress(),sysUser);
        String token = tokenService.createToken(loginUser);

        JSONObject obj=new JSONObject();
        obj.put("token",token);

        AsyncManager.me().execute(AsyncFactory.recordLogininfor(address, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        user.setLoginIp(IpUtils.getIpAddr());
        user.setLoginDate(new Date());
        userService.updateUser(user);

        return success(obj);
    }



    /**
     * 获取是否绑定上级地址
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/user/parent/info")
    @Anonymous
    public AjaxResult getAddressInfo(HttpServletResponse response, HttpServletRequest request,@RequestBody JSONObject param)
    {
        String address = param.getString("address").toLowerCase();
        CUser user = userService.getByAddress(address);
        JSONObject obj=new JSONObject();
        if(user==null||StringUtils.isEmpty(user.getParentAddress())){
            obj.put("result",false);
        }else{
            obj.put("result",true);
        }

        return AjaxResult.success(obj);
    }






    /**
     * 用户绑定邮箱
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/user/bind/email")
    @Anonymous
    public AjaxResult bindEmail(HttpServletResponse response, HttpServletRequest request, @RequestBody JSONObject param)
    {

        String address=getUserAddress();
        String email=param.getString("email").toLowerCase();
        String code=param.getString("code").toLowerCase();

        String cc=redisCache.getCacheObject(address+"_"+email);
        if(StringUtils.isEmpty(email)||StringUtils.isEmpty(code)){
            return error("参数错误");
        }
        if(StringUtils.isEmpty(cc)){
            return error("验证码已失效");
        }
        if(!code.equalsIgnoreCase(cc)){
            return error("验证码错误");
        }


        CUser user = userService.getByEmail(email);
        if(user !=null){
            return error("邮箱已绑定");
        }

         user = userService.getByAddress(address);
        if(StringUtils.isNotEmpty(user.getEmail())){
            return error("邮箱已绑定");
        }
        user.setEmail(email);
        userService.updateUser(user);
        redisCache.deleteObject(address+"_"+email);

        return success();
    }





}
