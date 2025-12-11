package com.ruoyi.business.service;

import com.ruoyi.business.mapper.CBalanceLogMapper;
import com.ruoyi.business.mapper.CBalanceMapper;
import com.ruoyi.business.mapper.CCurrencyMapper;
import com.ruoyi.business.mapper.CUserMapper;
import com.ruoyi.business.model.CBalance;
import com.ruoyi.business.model.CCurrency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
@Transactional(rollbackFor = Exception.class)
public class CurrencyService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    CCurrencyMapper currencyMapper;



    public CCurrency getCurrency(String cy) {
        Example ex=new Example(CCurrency.class);
        ex.createCriteria().andEqualTo("currency",cy);
        CCurrency currency = currencyMapper.selectOneByExample(ex);
        return currency;
    }

    public void update(CCurrency currency) {
        currencyMapper.updateByPrimaryKeySelective(currency);
    }

    public CCurrency getByContract(String contract) {
        Example ex=new Example(CCurrency.class);
        ex.createCriteria().andEqualTo("contractAddress",contract);
        return currencyMapper.selectOneByExample(ex);
    }

}
