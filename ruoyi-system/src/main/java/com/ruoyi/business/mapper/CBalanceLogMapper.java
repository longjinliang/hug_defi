package com.ruoyi.business.mapper;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.model.CBalanceLog;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.Date;
import java.util.List;

public interface CBalanceLogMapper extends Mapper<CBalanceLog>, MySqlMapper<CBalanceLog> {
    List<JSONObject> getBackBalanceLogList(@Param("address") String address,
                                           @Param("coin") String coin,
                                           @Param("startTime") Date startTime,
                                           @Param("endTime") Date endTime,
                                           @Param("balanceType") String balanceType);
}