package com.ruoyi.business.service;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.mapper.CRewardMapper;
import com.ruoyi.business.mapper.CWithdrawMapper;
import com.ruoyi.business.model.*;
import com.ruoyi.common.constant.PoolTypeConstants;
import com.ruoyi.common.utils.EncrypUtils;
import com.ruoyi.common.utils.EthSign;
import com.ruoyi.system.service.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class WithdrawService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    CWithdrawMapper withdrawMapper;
    @Autowired
    CurrencyService currencyService;
    @Autowired
    BalanceService balanceService;

    @Autowired
    ISysConfigService sysConfigService;

    @Autowired
    CRewardMapper rewardMapper;



    public CWithdraw getByHash(String hash) {
        Example ex=new Example(CWithdraw.class);
        ex.createCriteria().andEqualTo("hash",hash);
        return withdrawMapper.selectOneByExample(ex);
    }

    public int getWithdrawCount(String address) {
        Example ex=new Example(CWithdraw.class);
        ex.createCriteria().andEqualTo("address",address);
        return withdrawMapper.selectCountByExample(ex);
    }

    public void saveOrder(String userAddress, BigDecimal amount,
                          String hash, Integer withdrawType) {
        CWithdraw withdraw = getByHash(hash);
        if(withdraw !=null){
            return;
        }
        CUser user = userService.getByAddress(userAddress);
        if(user==null){
            return;
        }
        String coin=null;
        CBalance balance=null;
        if(withdrawType.intValue()==1){
            coin="USDT";
            balance=balanceService.getByAddress(coin,userAddress,"reward");
        }else if(withdrawType.intValue()==2){
            coin="HUG";
            balance=balanceService.getByAddress(coin,userAddress,"airdrop");
        }else if(withdrawType.intValue()==3){
            coin="HUG";
            balance=balanceService.getByAddress(coin,userAddress,"buy");
        }else{
            return;
        }
        CCurrency currency = currencyService.getCurrency(coin);


        withdraw=new CWithdraw();
        withdraw.setAddress(userAddress);
        withdraw.setUid(user.getUserId());
        withdraw.setCurrency(currency.getCurrency());
        withdraw.setCurrencyId(currency.getId());
        withdraw.setAmount(amount);
        withdraw.setHash(hash);
        withdraw.setStatus(3);
        withdraw.setType(withdrawType+"");
        withdraw.setRewardId(null);
        withdraw.setCreateTime(new Date());
        withdraw.setUpdateTime(new Date());
        withdrawMapper.insertSelective(withdraw);

        balanceService.addBalance(balance,amount.negate(),withdraw.getId(),"withdraw","提币");


    }

    public JSONObject getWithdrawSign(String address, Integer withdrawType) {

        String coin=null;
        CBalance balance=null;
        if(withdrawType.intValue()==1){
            coin="USDT";
            balance=balanceService.getByAddress(coin,address,"reward");
        }else if(withdrawType.intValue()==2){
            coin="HUG";
            balance=balanceService.getByAddress(coin,address,"airdrop");
        }else if(withdrawType.intValue()==3){
            coin="HUG";
            balance=balanceService.getByAddress(coin,address,"buy");
        }

        CCurrency currency = currencyService.getCurrency(coin);
        if(currency.getWithdrawEnabled().intValue()==0){
            throw new RuntimeException("该币种暂时不支持提币");
        }

        {

            int count = withdrawMapper.getTodayCount(address);
            if (count >= 2) {
                throw new RuntimeException("已超过每日提币次数2");
            }
        }

        if(balance.getAmount().compareTo(BigDecimal.ZERO)<=0){
            throw new RuntimeException("余额不足");
        }
           BigDecimal amount=balance.getAmount();

            Integer nonce = getWithdrawCount(address);
            nonce=nonce+1;
            long timeout=new Date().getTime()/1000+120;


            BigInteger bigAmount=amount.multiply(new BigDecimal(Math.pow(10,currency.getDecimals()))).toBigInteger();

            String key = sysConfigService.selectConfigByKey("spring_kk");
            String privateKey = EncrypUtils.decode("123456", key + "=");
            String sign = EthSign.getWithdrawSign(withdrawType,address, bigAmount, nonce, timeout, privateKey);

            JSONObject obj=new JSONObject();
            obj.put("amount","0x" + bigAmount.toString(16));
            obj.put("nonce",nonce);
            obj.put("poolType",withdrawType);
            obj.put("timeout",timeout);
            obj.put("signature",sign);
            return obj;

    }



    public List<CWithdraw> getWithrawRecord(String address) {
        Example ex=new Example(CWithdraw.class);
        ex.createCriteria().andEqualTo("address",address);
        ex.orderBy("createTime").desc();
        return withdrawMapper.selectByExample(ex);
    }

    public List<CWithdraw> getWithdrawList(String address, Date startTime, Date endTime, String coin, Integer status, String type) {
        return withdrawMapper.getWithdrawList(address,startTime,endTime,coin,status,type);
    }

    public List<JSONObject> getWithdrawListSum(String address, Date startTime, Date endTime, String coin, Integer status, String type) {
        return withdrawMapper.getWithdrawListSum(address,startTime,endTime,coin,status,type);
    }

//    public void addWithdraw(String address, String coin, BigDecimal amount) {
//        coin=coin.toUpperCase();
//        CBalance balance = balanceService.getByAddress(coin, address);
//        if(balance.getAmount().compareTo(amount)<0){
//            throw new RuntimeException("余额不足");
//        }
//
//        CUser user = userService.getByAddress(address);
//        CWithdraw withdraw=new CWithdraw();
//        withdraw.setUid(user.getUserId());
//        withdraw.setAddress(user.getAddress());
//        withdraw.setCurrency(coin);
//        withdraw.setCurrencyId(balance.getCurrencyId());
//        withdraw.setAmount(amount);
////        withdraw.set
//    }
}
