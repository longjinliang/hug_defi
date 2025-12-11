package com.ruoyi.business.service;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.mapper.CBalanceLogMapper;
import com.ruoyi.business.mapper.CBalanceMapper;
import com.ruoyi.business.mapper.CRewardMapper;
import com.ruoyi.business.model.*;
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
public class RewardService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    CurrencyService currencyService;

    @Autowired
    CRewardMapper  rewardMapper;


    public BigDecimal  getRewardSum(String address,String type){
        BigDecimal sum=rewardMapper.getRewardSum(address,type);
        if(sum==null){
            sum=new BigDecimal(0);
        }
        return sum;
    }


    public List<CReward> getRewardList(String address, List<String> types) {
//        Example example=new Example(CReward.class);
//        example.createCriteria().andEqualTo("address",address)
//                .andIn("rewardType",types);
//        example.orderBy("id").desc();
//        return rewardMapper.selectByExample(example);
        return rewardMapper.getRewardList(address,types);
    }

    public BigDecimal  getRewardSumByTypes(String address,List<String> types){
        BigDecimal sum = rewardMapper.getRewardSumByTypes(address,types);
        if(sum==null){
            sum=BigDecimal.ZERO;
        }
        return sum;
    }


    public List<CReward> getBackRewardList(String address, Date startTime, Date endTime, String type) {
        return rewardMapper.getBackRewardList(address,startTime,endTime,type);
    }

    public List<JSONObject> getBackRewardListSum(String address, Date startTime, Date endTime, String type) {
        return rewardMapper.getBackRewardListSum(address,startTime,endTime,type);
    }
}
