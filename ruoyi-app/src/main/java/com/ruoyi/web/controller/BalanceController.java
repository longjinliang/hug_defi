package com.ruoyi.web.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.model.CBalance;
import com.ruoyi.business.model.CUserStatics;
import com.ruoyi.business.service.BalanceService;
import com.ruoyi.business.service.RewardService;
import com.ruoyi.business.service.UserStaticsService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 *
 * 
 * @author ruoyi
 */
@RestController
public class BalanceController extends BaseController
{

    @Autowired
    private BalanceService balanceService;





    /**
     * 获取账户余额
     *
     * @param param
     * @return 结果
     */
    @PostMapping("/balance/info")
    public AjaxResult getBalanceInfo(HttpServletRequest request, @RequestBody JSONObject param)
    {
        String address = getUserAddress();
        CBalance airdropBalance = balanceService.getByAddress("HUG", address, "airdrop");
        CBalance buyBalance = balanceService.getByAddress("HUG", address, "buy");
        CBalance usdtBalance = balanceService.getByAddress("USDT", address, "reward");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("airdropAmount", airdropBalance.getAmount());
        jsonObject.put("buyAmount", buyBalance.getAmount());
        jsonObject.put("usdtAmount", usdtBalance.getAmount());

        return success(jsonObject);
    }












}
