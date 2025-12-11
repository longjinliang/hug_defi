package com.ruoyi.business.mapper;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.model.CReward;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CRewardMapper extends Mapper<CReward>, MySqlMapper<CReward> {
    BigDecimal getRewardUsdtSum();

    BigDecimal getRewardSum(@Param("address") String address, @Param("type") String type);

    BigDecimal getRewardSumByTypes(@Param("address") String address, @Param("types") List<String> types);

    List<CReward> getRewardList(@Param("address") String address, @Param("types") List<String> types);


    List<CReward> getBackRewardList(@Param("address") String address, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("type") String type);

    List<JSONObject> getBackRewardListSum(@Param("address") String address, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("type") String type);


}