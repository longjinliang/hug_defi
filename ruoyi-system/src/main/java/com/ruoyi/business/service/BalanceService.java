package com.ruoyi.business.service;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.mapper.*;
import com.ruoyi.business.model.*;
import com.ruoyi.common.utils.EncrypUtils;
import com.ruoyi.common.utils.EthSign;
import com.ruoyi.common.utils.IdWorker;
import com.ruoyi.system.service.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BalanceService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    CBalanceMapper balanceMapper;

    @Autowired
    CBalanceLogMapper balanceLogMapper;
    @Autowired
    CurrencyService currencyService;

    @Autowired
    UserTempService userTempService;




    public CBalance getByAddress(String cy, String address,String balanceType) {
        if(!balanceType.equalsIgnoreCase("reward")&&!balanceType.equalsIgnoreCase("buy")
                &&!balanceType.equalsIgnoreCase("airdrop")

        ){
            return null;
        }
        Example ex=new Example(CBalance.class);
        ex.createCriteria().andEqualTo("currency",cy)
                .andEqualTo("balanceType",balanceType)
                .andEqualTo("address",address);
        CBalance balance = balanceMapper.selectOneByExample(ex);
        if(balance==null){
            CUser user = userService.getByAddress(address);
            CCurrency currency = currencyService.getCurrency(cy);
            balance=new CBalance();
            balance.setAddress(address);
            balance.setUid(user.getUserId());
            balance.setCurrency(cy);
            balance.setCurrencyId(currency.getId());
            balance.setBalanceType(balanceType);
            balance.setVersion(0);
            balance.setAmount(BigDecimal.ZERO);
            balance.setCreateTime(new Date());
            balance.setUpdateTime(new Date());
            balance.setVersion(0);
            balanceMapper.insertSelective(balance);

//            userTempService.updateBalance(balance);

            return balance;
        }else{
//            userTempService.checkBalance(balance);
        }
        return balance;
    }


    public int addBalance(CBalance balance, BigDecimal amount, long orderId, String orderType, String remark) {

        CBalanceLog balanceLog=new CBalanceLog();

        balanceLog.setBalanceId(balance.getId());
        balanceLog.setUid(balance.getUid());
        balanceLog.setBeforeAmount(balance.getAmount());
        balanceLog.setAfterAmount(balance.getAmount().add(amount));
        balanceLog.setFid(orderId);
        balanceLog.setAmount(amount);
        balanceLog.setRemarks(remark);
        balanceLog.setCreateTime(new Date());
        balanceLog.setUpdateTime(new Date());
        balanceLog.setType(orderType);
        balanceLogMapper.insertSelective(balanceLog);


        int i = balanceMapper.addBalance(balance.getId(), amount,balance.getVersion());

        if(i==0){
            throw new RuntimeException("更新资产异常");
        }
//        userTempService.updateBalance(balance);

        return i;

    }

    public int frozenBalance(CBalance balance, BigDecimal amount, BigDecimal frozenAmount,long orderId, String orderType, String remark) {

        CBalanceLog balanceLog=new CBalanceLog();

        balanceLog.setBalanceId(balance.getId());
        balanceLog.setUid(balance.getUid());
        balanceLog.setBeforeAmount(balance.getAmount());
        balanceLog.setAfterAmount(balance.getAmount().add(amount));
        balanceLog.setFid(orderId);
        balanceLog.setAmount(amount);
        balanceLog.setRemarks(remark);
        balanceLog.setCreateTime(new Date());
        balanceLog.setUpdateTime(new Date());
        balanceLog.setType(orderType);
        balanceLogMapper.insertSelective(balanceLog);


        int i = balanceMapper.frozenBalance(balance.getId(), amount,frozenAmount,balance.getVersion());

        if(i==0){
            throw new RuntimeException("更新资产异常");
        }
//        userTempService.updateBalance(balance);

        return i;

    }

    public List<JSONObject> getBalanceList(String address, String coin,String balanceType) {
        return balanceMapper.getBalanceList(address,coin,balanceType);
    }

    public List<CBalance> getBackBalanceList(String address, String coin, String balanceType) {
        return balanceMapper.getBackBalanceList(address,coin,balanceType);
    }

    public List<JSONObject> getBackBalanceLogList(String address, String coin, Date startTime, Date endTime, String balanceType) {
        return balanceLogMapper.getBackBalanceLogList(address,coin,startTime,endTime,balanceType);
    }

    public BigDecimal getSumAmount(String currency) {
        return balanceMapper.getSumAmount(currency);
    }

    public List<JSONObject> getBackBalanceListSum(String address, String coin, String balanceType) {
        return balanceMapper.getBackBalanceListSum(address,coin,balanceType);
    }
}
