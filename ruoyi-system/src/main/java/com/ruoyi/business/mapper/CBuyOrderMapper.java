package com.ruoyi.business.mapper;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.model.CBuyOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CBuyOrderMapper extends Mapper<CBuyOrder>, MySqlMapper<CBuyOrder> {

    BigDecimal getUserBuyUsdtAmount(@Param("address") String address);

    List<JSONObject> getBuyList();

    BigDecimal getMyBuyTokenAmount(@Param("address") String address);

    BigDecimal getMyBuyUsdtAmount(@Param("address") String address);

    List<JSONObject> getMyBuyList(@Param("address") String address);

    List<CBuyOrder> getBackBuyList(@Param("address") String address, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    JSONObject getBackBuyListSum(@Param("address") String address, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}