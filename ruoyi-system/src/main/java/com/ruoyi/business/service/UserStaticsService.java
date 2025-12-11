package com.ruoyi.business.service;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.mapper.CUserMapper;
import com.ruoyi.business.mapper.CUserStaticsMapper;
import com.ruoyi.business.model.CUser;
import com.ruoyi.business.model.CUserStatics;
import com.ruoyi.common.core.redis.RedisCache;
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
public class UserStaticsService {
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
    RedisCache redisCache;

    @Autowired
    BalanceService balanceService;

    @Autowired
    CUserStaticsMapper userStaticsMapper;

    @Autowired
    UserTempService userTempService;



    public CUserStatics getByAddress(String address) {
        Example ex=new Example(CUserStatics.class);
        ex.createCriteria().andEqualTo("address",address);
        CUserStatics statics = userStaticsMapper.selectOneByExample(ex);
        if(statics==null){
            CUser user = userService.getByAddress(address);
            statics=new CUserStatics();
            statics.setAddress(user.getAddress());
            statics.setUid(user.getUserId());
            statics.setInviteCount(0);
            statics.setTeamCount(0);
            statics.setTotalUserAmount(BigDecimal.ZERO);
            statics.setTotalTeamAmount(BigDecimal.ZERO);
            statics.setTotalUserCount(0);
            statics.setTotalTeamCount(0);
            statics.setTeamLevel(0);
            statics.setTeamLevelName("LV0");
            statics.setLastPayTime(null);
            statics.setNodeLevel(0);
            statics.setBuyTokenAmount(BigDecimal.ZERO);
            statics.setBuyUsdtAmount(BigDecimal.ZERO);
            statics.setCreateTime(new Date());
            statics.setUpdateTime(new Date());
            userStaticsMapper.insertSelective(statics);

        }else{
        }
        return statics;
    }

    public CUserStatics getByAddressForUpdate(String address) {
        CUserStatics userStatics = getByAddress(address);
        userStatics=userStaticsMapper.getByAddressForUpdate(address);
        return userStatics;
    }

    public void updateStatics(CUserStatics statics) {
        userStaticsMapper.updateByPrimaryKeySelective(statics);
    }


    public List<JSONObject> getUserDirectList(String address) {
        return userStaticsMapper.getUserDirectList(address);
    }
}
