package com.ruoyi.business.mapper;

import com.ruoyi.business.model.CArbBlockNum;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface CArbBlockNumMapper extends Mapper<CArbBlockNum>, MySqlMapper<CArbBlockNum> {
    Integer getMaxNum();
}