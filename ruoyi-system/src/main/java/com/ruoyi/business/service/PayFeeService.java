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
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(rollbackFor = Exception.class)
public class PayFeeService {
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
    CFeeOrderMapper feeOrderMapper;

    @Autowired
    RedisCache redisCache;

    @Autowired
    ReceiveAddressService receiveAddressService;

    @Autowired
    BalanceService balanceService;

    @Autowired
    CRewardMapper rewardMapper;
    @Autowired
    UserStaticsService userStaticsService;
    @Autowired
    CTeamConfigMapper teamConfigMapper;
    @Autowired
    CPayCountRecordMapper payCountRecordMapper;
    @Autowired
    AddressVerifiedService addressVerifiedService;






    public JSONObject getPayFeeSign(String address,BigDecimal amount) {

        CAddressVerified addressVerified = addressVerifiedService.getByAddress(address);
        if(addressVerified==null||addressVerified.getStatus().intValue() !=2){
            throw new RuntimeException(MessageUtils.message("您尚未完成KYC认证","airdrop.kyc.notfinish"));
        }

        int todayCount=feeOrderMapper.getTodayCount(address);

        if(todayCount>=4){
            throw new RuntimeException(MessageUtils.message("您今日领取已结束","airdrop.user.today.finish"));
        }

        BigDecimal totalTokenAmount=getTotalTokenAmount();

        String str = sysConfigService.selectNoCacheConfigByKey("pay_fee_config");
        JSONObject config = JSON.parseObject(str);
        BigDecimal tta = config.getBigDecimal("totalTokenAmount");
        if(totalTokenAmount.compareTo(tta)>=0){
            throw new RuntimeException(MessageUtils.message("本次空投已结束","airdrop.finish"));
        }



        CFeeOrder todayOrder = feeOrderMapper.getTodayOrder(address);
        int roundMinutes = config.getIntValue("roundMinutes");
        if(todayOrder !=null){
            Date takeTime = DateUtils.addMinutes(todayOrder.getCreateTime(), roundMinutes);
            if(new Date().compareTo(takeTime)<0){
                throw new RuntimeException(MessageUtils.message("暂未到时间领取","airdrop.notto.time"));
            }
        }

        BigDecimal tokenAmount=new BigDecimal(todayCount+1).multiply(new BigDecimal(5));


        int userPayCount = getUserPayCount(address);
        int nonce=userPayCount+1;

        if(amount ==null){
            amount = getDayPayFeeAmount(address);
        }

        long timeout=new Date().getTime()/1000+90;

        String key = sysConfigService.selectConfigByKey("spring_kk");
        String privateKey = EncrypUtils.decode("123456", key + "=");

        BigInteger bigAmount = amount.multiply(new BigDecimal(Math.pow(10, 18))).toBigInteger();
        BigInteger tokenBigAmount = tokenAmount.multiply(new BigDecimal(Math.pow(10, 18))).toBigInteger();

        String sign = EthSign.getPayFeeSign(address,bigAmount,tokenBigAmount,timeout, nonce,privateKey);


        JSONObject obj=new JSONObject();
        obj.put("amount",bigAmount.toString());
        obj.put("tokenAmount",tokenBigAmount.toString());
        obj.put("timeout",timeout);
        obj.put("signature",sign);
        obj.put("nonce",nonce);

        return obj;
    }

    public int getTodayCount(String address){
        return feeOrderMapper.getTodayCount(address);
    }

    public BigDecimal getDayPayFeeAmount(String address){
        int date = new Date().getDate();
        BigDecimal amount=redisCache.getCacheObject(address+":pay_fee_amount:"+date);
        if(amount==null){
            String str = sysConfigService.selectNoCacheConfigByKey("pay_fee_config");
            JSONObject config = JSON.parseObject(str);
            BigDecimal min = config.getBigDecimal("min");
            BigDecimal max = config.getBigDecimal("max");


            int randomInt = (int)(Math.random() * (max.multiply(new BigDecimal("1000")).intValue() - min.multiply(new BigDecimal("1000")).intValue() + 1)) + min.multiply(new BigDecimal("1000")).intValue();

            CCurrency ethCurrency = currencyService.getCurrency("ETH");

            amount=new BigDecimal(randomInt).divide(new BigDecimal("1000"),4,BigDecimal.ROUND_DOWN)
                    .divide(ethCurrency.getUsdtPrice(),10,BigDecimal.ROUND_DOWN);

            redisCache.setCacheObject(address+":pay_fee_amount:"+date,amount,1, TimeUnit.DAYS);

           logger.info("随机支付数量usdt：{},eth:{}",new BigDecimal(randomInt).divide(new BigDecimal("1000"),4,BigDecimal.ROUND_DOWN),amount); ;
        }
        return amount;
    }



    public int getUserPayCount(String address){
        Example ex=new Example(CFeeOrder.class);
        ex.createCriteria().andEqualTo("address",address);
        int userPayCount = feeOrderMapper.selectCountByExample(ex);
        return userPayCount;
    }

    public void savePayFee (String address, BigDecimal amount, String receiveAddress, String hash, BigDecimal tokenAmount) {

        CUser user = userService.getByAddress(address);
        if(user==null){
            return;
        }
        CFeeOrder order=getPayOrderByHash(hash);
        if(order !=null) return;


        CCurrency ethCurrency = currencyService.getCurrency("ETH");
        order=new CFeeOrder();
        order.setAddress(address);
        order.setToAddress(receiveAddress);
        order.setCoin(ethCurrency.getCurrency());
        order.setAmount(amount);
        order.setUsdtAmount(amount.multiply(ethCurrency.getUsdtPrice()).setScale(6,BigDecimal.ROUND_DOWN));
        order.setStatus(1);
        order.setTokenAmount(tokenAmount);
        order.setHash(hash);
        order.setLoginIp(user.getLoginIp());
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());

        if(amount.compareTo(BigDecimal.ZERO)<=0){
            order.setTakeFlag(1);
        }else{
            order.setTakeFlag(0);
        }

        feeOrderMapper.insertSelective(order);

        CBalance balance = balanceService.getByAddress("HUG", address, "airdrop");
        balanceService.addBalance(balance,tokenAmount,order.getId(),"airdrop","领取空投");

        CUserStatics userStatics = userStaticsService.getByAddressForUpdate(address);
        userStatics.setTotalUserAmount(userStatics.getTotalUserAmount().add(tokenAmount));
        userStatics.setTotalUserCount(userStatics.getTotalUserCount() + 1);
        userStatics.setUpdateTime(new Date());
        userStatics.setLastPayTime(new Date());

        int todayCount = feeOrderMapper.getTodayCount(address);

        userStatics.setUserTodayCount(todayCount);

        userStaticsService.updateStatics(userStatics);


        if(todayCount>=3){
            //团队奖励
            List<CTeamConfig> teamConfigList = getTeamConfigList();
            teamConfigList.sort((a,b)->a.getLevel()-b.getLevel());
            if(StringUtils.isNotEmpty(user.getParentAddress())){
                CUser parent=userService.getByAddress(user.getParentAddress());
                while (true){
                    if(parent==null){
                        break;
                    }
                    int depth = user.getUserDepth() - parent.getUserDepth();
                    if(depth>10){
                        break;
                    }

                    //推荐多少位实名人数
                    int veriviedCount=userService.getVerifiedCount(parent.getAddress());

                    int recordCount=3;
                    if(todayCount !=3){
                        recordCount=1;
                    }

                    if(veriviedCount>=depth){

                        for (int i = 0; i < recordCount; i++) {
                            CPayCountRecord payCountRecord=new CPayCountRecord();
                            payCountRecord.setAddress(parent.getAddress());
                            payCountRecord.setOrderId(order.getId());
                            payCountRecord.setOrderAddress(order.getAddress());
                            payCountRecord.setChildLevel(depth);
                            payCountRecord.setCreateTime(new Date());
                            payCountRecord.setUpdateTime(new Date());
                            payCountRecordMapper.insertSelective(payCountRecord);
                        }



                        CUserStatics parentStatics = userStaticsService.getByAddressForUpdate(parent.getAddress());
                        parentStatics.setTotalTeamCount(parentStatics.getTotalTeamCount() + recordCount);

                        for(CTeamConfig teamConfig:teamConfigList){
                            if(parentStatics.getTeamLevel().intValue()<teamConfig.getLevel().intValue()
                                    && parentStatics.getTotalTeamCount().intValue()>=teamConfig.getTeamCount().intValue()
                            ){
                                parentStatics.setTeamLevelName("LV"+teamConfig.getLevel());
                                parentStatics.setTeamLevel(teamConfig.getLevel());

                                Example ex=new Example(CReward.class);
                                ex.createCriteria().andEqualTo("address",parent.getAddress())
                                        .andEqualTo("teamLevel",teamConfig.getLevel());
                                int rewardCount = rewardMapper.selectCountByExample(ex);
                                if(rewardCount==0){
                                    CReward reward=new CReward();
                                    reward.setAddress(parent.getAddress());
                                    reward.setCoin("USDT");
                                    reward.setRewardAmount(teamConfig.getRewardAmount());
                                    reward.setOrderId(order.getId());
                                    reward.setRewardType("team");
                                    reward.setTeamLevel(teamConfig.getLevel());
                                    reward.setTeamCount(teamConfig.getTeamCount());
                                    reward.setRemark("团队奖励");
                                    reward.setIsTake(0);
                                    reward.setWithdrawId(0L);
                                    reward.setCreateTime(new Date());
                                    reward.setUpdateTime(new Date());
                                    rewardMapper.insertSelective(reward);

                                    CBalance parentBalance  = balanceService.getByAddress(reward.getCoin(), parent.getAddress(), "reward");
                                    balanceService.addBalance(parentBalance,reward.getRewardAmount(),reward.getId(),"team","团队奖励");

                                }
                            }
                        }

                        userStaticsService.updateStatics(parentStatics);
                    }


                    if(StringUtils.isEmpty(parent.getParentAddress())){
                        break;
                    }
                    parent=userService.getByAddress(parent.getParentAddress());

                }
            }
        }



    }

    public CFeeOrder getTodayOrder(String address) {
        return feeOrderMapper.getTodayOrder(address);
    }

    public CFeeOrder getYesterdayOrder(String address) {
        return feeOrderMapper.getYesterdayOrder(address);
    }


    public CFeeOrder getPayOrderByHash(String hash) {
        Example ex=new Example(CFeeOrder.class);
        ex.createCriteria().andEqualTo("hash",hash);
        return feeOrderMapper.selectOneByExample(ex);
    }

    public Integer getPayCount(){
        return feeOrderMapper.getPayCount();
    }


    public int getPayCountByAddress(String address, Date fromTime, Date toTime) {
        Example ex=new Example(CFeeOrder.class);
        Example.Criteria criteria = ex.createCriteria().andEqualTo("address", address);
        if(fromTime !=null){
            criteria.andGreaterThan("createTime",fromTime);
        }
        if(toTime !=null){
            criteria.andLessThan("createTime",toTime);
        }
        return feeOrderMapper.selectCountByExample(ex);
    }


    public List<JSONObject> getPayFeeList(int pageNum, int pageSize, String address) {
        PageHelper.startPage(pageNum,pageSize);
//        Example ex=new Example(CFeeOrder.class);
//        ex.orderBy("id").desc();
//        return feeOrderMapper.selectByExample(ex);
        return feeOrderMapper.getPayFeeList(address);
    }

    public int getUserCount() {
        return feeOrderMapper.getUserCount();
    }


    public CFeeOrder getLastOrder(String address) {
        return feeOrderMapper.getLastOrder(address);
    }



    public List<CFeeOrder> getOrdersByTime(Date startTime, Date endTime) {
        Example ex=new Example(CFeeOrder.class);
        ex.createCriteria().andGreaterThanOrEqualTo("createTime",startTime)
                .andLessThanOrEqualTo("createTime",endTime)
                .andIsNull("loginIp");
        return feeOrderMapper.selectByExample(ex);
    }

    public List<CFeeOrder> getIpNullOrders() {
        Example ex=new Example(CFeeOrder.class);
        ex.createCriteria()
                .andIsNull("loginIp");
        return feeOrderMapper.selectByExample(ex);
    }

    public void updateOrder(CFeeOrder order) {
        feeOrderMapper.updateByPrimaryKeySelective(order);
    }


    public BigDecimal getUsdtSum() {
        return feeOrderMapper.getUsdtSum();
    }

    public BigDecimal getRewardUsdtSum() {
        return rewardMapper.getRewardUsdtSum();
    }

    public List<CTeamConfig> getTeamConfigList() {
        Example ex=new Example(CTeamConfig.class);
        ex.orderBy("level").asc();
        return teamConfigMapper.selectByExample(ex);
    }

    public JSONObject getUserPaySum(String address) {
        return feeOrderMapper.getUserPaySum(address);
    }


    public BigDecimal getTotalTokenAmount() {
        return feeOrderMapper.getTotalTokenAmount();
    }

    public int getTeamPayCount(String userTree) {
        return feeOrderMapper.getTeamPayCount(userTree);
    }

    public int getTeamEffectCount(String userTree) {
        return feeOrderMapper.getTeamEffectCount(userTree);
    }

    public List<CFeeOrder> getBackPayFeeList(String address, Date startTime, Date endTime) {
        return feeOrderMapper.getBackPayFeeList(address,startTime,endTime);
    }

    public JSONObject getBackPayFeeListSum(String address, Date startTime, Date endTime) {
        return feeOrderMapper.getBackPayFeeListSum(address,startTime,endTime);
    }

    public List<JSONObject> getBackPayFeeStatics() {

        return feeOrderMapper.getBackPayFeeStatics();
    }
}
