package com.ruoyi.business.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ruoyi.business.mapper.*;
import com.ruoyi.business.model.*;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.*;
import com.ruoyi.system.service.ISysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(rollbackFor = Exception.class)
public class BuyService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CUserMapper userMapper;

    @Autowired
    ISysConfigService sysConfigService;

    @Autowired
    UserService userService;
    @Autowired
    CurrencyService currencyService;

    @Autowired
    UserTempService userTempService;


    @Autowired
    RedisCache redisCache;


    @Autowired
    BalanceService balanceService;

    @Autowired
    CRewardMapper rewardMapper;

    @Autowired
    UserStaticsService userStaticsService;
    @Autowired
    CTeamConfigMapper teamConfigMapper;

    @Autowired
    CBuyStageMapper buyStageMapper;
    @Autowired
    CBuyOrderMapper buyOrderMapper;




    public JSONObject getBuySign(String address,BigDecimal usdtAmount) {

        Example example=new Example(CBuyStage.class);
        example.createCriteria().andEqualTo("status",1);

        CBuyStage buyStage = buyStageMapper.selectOneByExample(example);
        if(buyStage ==null){
            throw new RuntimeException(MessageUtils.message("认购已结束","buy.finish"));
        }else if(buyStage.getBuyAmount().compareTo(buyStage.getTotalAmount())>=0){
            throw new RuntimeException(MessageUtils.message("认购已售罄","buy.empty"));
        }

        String s = sysConfigService.selectNoCacheConfigByKey("buy_config");
        JSONObject config = JSON.parseObject(s, JSONObject.class);

        BigDecimal firstMinUsdtAmount = config.getBigDecimal("firstMinUsdtAmount");
        BigDecimal minUsdtAmount = config.getBigDecimal("minUsdtAmount");
        BigDecimal userMaxUsdtAmount = config.getBigDecimal("userMaxUsdtAmount");


        usdtAmount=usdtAmount.setScale(0,BigDecimal.ROUND_DOWN);

        int orderCount = getUserOrderCount(address);

        if(usdtAmount.compareTo(firstMinUsdtAmount)<0 && orderCount==0){
            throw new RuntimeException(MessageUtils.message("首次最低认购","buy.first.min")+firstMinUsdtAmount.stripTrailingZeros().toPlainString()+" USDT");
        }
        if(usdtAmount.compareTo(minUsdtAmount)<0 && orderCount>0){
            throw new RuntimeException(MessageUtils.message("最低认购","buy.min")+minUsdtAmount.stripTrailingZeros().toPlainString()+" USDT");
        }

        if(usdtAmount.intValue()%100 >0){
            throw new RuntimeException(MessageUtils.message("认购金额需是100的倍数","buy.money.must100"));
        }


        BigDecimal tokenAmount=usdtAmount.divide(buyStage.getUsdtPrice(),6,BigDecimal.ROUND_DOWN);

        {
            BigDecimal subTokenAmount = buyStage.getTotalAmount().subtract(buyStage.getBuyAmount());
            if (subTokenAmount.compareTo(BigDecimal.ZERO) <= 0) {
                subTokenAmount = BigDecimal.ZERO;
            }

            if (tokenAmount.compareTo(subTokenAmount) > 0) {
                tokenAmount = subTokenAmount;
                usdtAmount = tokenAmount.multiply(buyStage.getUsdtPrice());
            }
            if (tokenAmount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException(MessageUtils.message("认购已售罄","buy.empty"));
            }
        }

        {

            BigDecimal myBuyUsdtAmount = getMyBuyUsdtAmount(address);
            if (myBuyUsdtAmount == null) {
                myBuyUsdtAmount = BigDecimal.ZERO;
            }
            BigDecimal subtract = userMaxUsdtAmount.subtract(myBuyUsdtAmount);
            if(subtract.compareTo(BigDecimal.ZERO)<0){
                subtract = BigDecimal.ZERO;
            }
            if(usdtAmount.compareTo(subtract)>0){
                throw new RuntimeException(MessageUtils.message("剩余认购额度为","buy.user.remain")+subtract.stripTrailingZeros().toPlainString()+" USDT");
            }
        }



        String parentAddress="0x0000000000000000000000000000000000000000";
        BigDecimal parentUsdtAmount=BigDecimal.ZERO;

        String nodeAddress="0x0000000000000000000000000000000000000000";
        BigDecimal nodeUsdtAmount=BigDecimal.ZERO;

        CUser user = userService.getByAddress(address);

        BigDecimal rewardRatio = config.getBigDecimal("directRewardRatio");
        BigDecimal nodeRatio = config.getBigDecimal("nodeRewardRatio");


        if(StringUtils.isNotEmpty(user.getParentAddress())
                && rewardRatio.compareTo(BigDecimal.ZERO)>0
                && rewardRatio.compareTo(BigDecimal.ONE)<0){
            CUser parent = userService.getByAddress(user.getParentAddress());
            if(parent!=null){
                CUserStatics parentStatics = userStaticsService.getByAddress(parent.getAddress());
                if(parentStatics.getBuyUsdtAmount().compareTo(BigDecimal.ZERO)>0){
                    parentAddress=parent.getAddress();
                    parentUsdtAmount=usdtAmount.multiply(rewardRatio);
                }
            }
            if(nodeRatio.compareTo(BigDecimal.ZERO)>0&& nodeRatio.compareTo(BigDecimal.ONE)<0){
                while (true){
                    if(parent==null){
                        break;
                    }

                    CUserStatics parentStatics = userStaticsService.getByAddress(parent.getAddress());
                    if(parentStatics.getNodeLevel().intValue()>0){
                        nodeAddress=parent.getAddress();
                        nodeUsdtAmount=usdtAmount.multiply(nodeRatio);
                        break;
                    }

                    if(StringUtils.isEmpty(parent.getAddress())){
                        break;
                    }
                    parent=userService.getByAddress(parent.getParentAddress());

                }
            }
        }




        CCurrency currency = currencyService.getCurrency("USDT");


        int nonce=orderCount+1;


        long timeout=new Date().getTime()/1000+90;

        String key = sysConfigService.selectConfigByKey("spring_kk");
        String privateKey = EncrypUtils.decode("123456", key + "=");

        BigInteger usdtBigAmount = usdtAmount.multiply(new BigDecimal(Math.pow(10, currency.getDecimals()))).toBigInteger();

        BigInteger tokenBigAmount=tokenAmount.multiply(new BigDecimal(Math.pow(10, 18))).toBigInteger();

        BigInteger parentBigAmount = parentUsdtAmount.multiply(new BigDecimal(Math.pow(10, currency.getDecimals()))).toBigInteger();
        BigInteger nodeBigAmount=nodeUsdtAmount.multiply(new BigDecimal(Math.pow(10, currency.getDecimals()))).toBigInteger();


        String sign = EthSign.getBuySign(address,usdtBigAmount,tokenBigAmount,
                parentAddress,parentBigAmount,
                nodeAddress,nodeBigAmount,
                timeout, nonce,privateKey);


        JSONObject obj=new JSONObject();
        obj.put("usdtAmount",usdtBigAmount.toString());
        obj.put("tokenAmount",tokenBigAmount.toString());
        obj.put("parentAddress",parentAddress);
        obj.put("parentRewardAmount",parentBigAmount.toString());
        obj.put("nodeAddress",nodeAddress);
        obj.put("nodeRewardAmount",nodeBigAmount.toString());
        obj.put("timeout",timeout);
        obj.put("signature",sign);
        obj.put("nonce",nonce);

        return obj;
    }


    public int getUserOrderCount(String address){
        Example ex=new Example(CBuyOrder.class);
        ex.createCriteria().andEqualTo("address",address);
        int userPayCount = buyOrderMapper.selectCountByExample(ex);
        return userPayCount;
    }

    public void saveOrder (String address, BigDecimal usdtAmount, BigDecimal tokenAmount, String hash,String parentAddress,BigDecimal directRewardAmount,String nodeAddress,BigDecimal nodeRewardAmount) {

        CUser user = userService.getByAddress(address);
        if(user==null){
            return;
        }
        int count = getByHash(hash);
        if(count>0){
            return;
        }


        Example example=new Example(CBuyStage.class);
        example.createCriteria().andEqualTo("status",1);
        CBuyStage buyStage = buyStageMapper.selectOneByExample(example);


        CBuyOrder order=new CBuyOrder();
        order.setAddress(address);
        order.setToAddress("");
        order.setStageNum(buyStage.getStageNum());
        order.setCoin("USDT");
        order.setUsdtAmount(usdtAmount);
        order.setPrice(buyStage.getUsdtPrice());
        order.setStatus(1);
        order.setTokenAmount(tokenAmount);
        order.setHash(hash);
        order.setLoginIp(user.getLoginIp());
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        buyOrderMapper.insertSelective(order);

        CBalance balance = balanceService.getByAddress("HUG", address, "buy");
        balanceService.addBalance(balance,tokenAmount,order.getId(),"buy","认购");


        CUserStatics userStatics = userStaticsService.getByAddress(address);
        userStatics.setBuyUsdtAmount(userStatics.getBuyUsdtAmount().add(usdtAmount));
        userStatics.setBuyTokenAmount(userStatics.getBuyTokenAmount().add(tokenAmount));
        userStatics.setUpdateTime(new Date());

        String s = sysConfigService.selectNoCacheConfigByKey("buy_config");
        JSONObject config = JSON.parseObject(s, JSONObject.class);
        BigDecimal nodeUsdtAmount = config.getBigDecimal("nodeUsdtAmount");

        BigDecimal totalUsdtAmount=getUserBuyUsdtAmount(address);

        if(totalUsdtAmount.compareTo(nodeUsdtAmount)>=0){
            userStatics.setNodeLevel(1);
        }
        userStaticsService.updateStatics(userStatics);

        buyStage.setBuyAmount(buyStage.getBuyAmount().add(tokenAmount));
        buyStageMapper.updateByPrimaryKeySelective(buyStage);


        if(directRewardAmount.compareTo(BigDecimal.ZERO)>0){
            CUser parent = userService.getByAddress(parentAddress);
            //奖励记录
            CReward reward=new CReward();
            reward.setAddress(parent.getAddress());
            reward.setCoin("USDT");
            reward.setRewardAmount(directRewardAmount);
            reward.setUsdtAmount(directRewardAmount);
            reward.setOrderId(order.getId());
            reward.setRewardType("direct_buy_reward");
            reward.setTeamLevel(null);
            reward.setRemark("直推认购奖励");
            reward.setIsTake(0);
            reward.setWithdrawId(0L);
            reward.setCreateTime(new Date());
            reward.setUpdateTime(new Date());
            rewardMapper.insertSelective(reward);
        }

        if(nodeRewardAmount.compareTo(BigDecimal.ZERO)>0){
            CUser parent = userService.getByAddress(nodeAddress);
            //奖励记录
            CReward reward=new CReward();
            reward.setAddress(parent.getAddress());
            reward.setCoin("USDT");
            reward.setRewardAmount(nodeRewardAmount);
            reward.setUsdtAmount(nodeUsdtAmount);
            reward.setOrderId(order.getId());
            reward.setRewardType("node_buy_reward");
            reward.setTeamLevel(null);
            reward.setRemark("节点奖励");
            reward.setIsTake(0);
            reward.setWithdrawId(0L);
            reward.setCreateTime(new Date());
            reward.setUpdateTime(new Date());
            rewardMapper.insertSelective(reward);
        }


    }

    private BigDecimal getUserBuyUsdtAmount(String address) {
        return buyOrderMapper.getUserBuyUsdtAmount(address);
    }


    public int getByHash(String hash){
        Example ex=new Example(CBuyOrder.class);
        ex.createCriteria().andEqualTo("hash",hash);
        return buyOrderMapper.selectCountByExample(ex);
    }


    public List<JSONObject> getBuyList() {
        return buyOrderMapper.getBuyList();
    }

    public BigDecimal getMyBuyTokenAmount(String address) {
        return buyOrderMapper.getMyBuyTokenAmount(address);
    }

    public BigDecimal getMyBuyUsdtAmount(String address) {
        return buyOrderMapper.getMyBuyUsdtAmount(address);
    }

    public CBuyStage getCurrentStage() {

        Example example=new Example(CBuyStage.class);
        example.createCriteria().andEqualTo("status",1);
        CBuyStage buyStage = buyStageMapper.selectOneByExample(example);
        return buyStage;
    }

    public List<JSONObject> getMyBuyList(String address) {

        return buyOrderMapper.getMyBuyList(address);
    }

    public List<CBuyOrder> getBackBuyList(String address, Date startTime, Date endTime) {
        return buyOrderMapper.getBackBuyList(address,startTime,endTime);
    }

    public JSONObject getBackBuyListSum(String address, Date startTime, Date endTime) {
        return buyOrderMapper.getBackBuyListSum(address,startTime,endTime);
    }
}
