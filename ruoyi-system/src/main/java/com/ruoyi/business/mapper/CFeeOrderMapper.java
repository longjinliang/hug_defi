package com.ruoyi.business.mapper;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.model.CFeeOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface CFeeOrderMapper extends Mapper<CFeeOrder>, MySqlMapper<CFeeOrder> {
    Integer getPayCount();

    int getUserCount();

    BigDecimal getUsdtSum();

    List<JSONObject> getPayFeeList(@Param("address") String address);

    CFeeOrder getLastOrder(@Param("address") String address);

    CFeeOrder getYesterdayOrder(@Param("address") String address);

    CFeeOrder getTodayOrder(@Param("address") String address);

    int getPayCountByTime(@Param("address") String address,@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    JSONObject getUserPaySum(@Param("address") String address);

    int getTodayCount(@Param("address") String address);

    BigDecimal getTotalTokenAmount();

    int getTeamPayCount(@Param("userTree") String userTree);

    int getTeamEffectCount(@Param("userTree") String userTree);

    List<CFeeOrder> getBackPayFeeList(@Param("address") String address, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    JSONObject getBackPayFeeListSum(@Param("address") String address, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<JSONObject> getBackPayFeeStatics();
}