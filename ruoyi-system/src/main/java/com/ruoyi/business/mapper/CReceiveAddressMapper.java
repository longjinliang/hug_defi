package com.ruoyi.business.mapper;

import com.ruoyi.business.model.CReceiveAddress;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface CReceiveAddressMapper extends Mapper<CReceiveAddress>, MySqlMapper<CReceiveAddress> {
    CReceiveAddress getNextAddress();
}