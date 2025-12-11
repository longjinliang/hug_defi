package com.ruoyi.business.mapper;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.model.CWithdraw;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.Date;
import java.util.List;

public interface CWithdrawMapper extends Mapper<CWithdraw>, MySqlMapper<CWithdraw> {
    int getTodayCount(@Param("address") String address);

    List<CWithdraw> getWithdrawList(@Param("address") String address, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("coin") String coin, @Param("status") Integer status, @Param("type") String type);

    List<JSONObject> getWithdrawListSum(@Param("address") String address, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("coin") String coin, @Param("status") Integer status, @Param("type") String type);


}