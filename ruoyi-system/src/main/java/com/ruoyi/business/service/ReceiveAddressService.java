package com.ruoyi.business.service;

import com.ruoyi.business.mapper.CReceiveAddressMapper;
import com.ruoyi.business.model.CReceiveAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ReceiveAddressService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CReceiveAddressMapper receiveAddressMapper;




    public CReceiveAddress getNextAddress() {
        return receiveAddressMapper.getNextAddress();
    }

    public void save(CReceiveAddress receiveAddress) {
        receiveAddressMapper.updateByPrimaryKeySelective(receiveAddress);
    }

    public List<CReceiveAddress> getOtherAddress() {
        Example ex=new Example(CReceiveAddress.class);
        ex.createCriteria().andIn("pk", Arrays.asList("1","2"));
        return receiveAddressMapper.selectByExample(ex);
    }

    public CReceiveAddress getById(long id) {
        return receiveAddressMapper.selectByPrimaryKey(id);
    }

}
