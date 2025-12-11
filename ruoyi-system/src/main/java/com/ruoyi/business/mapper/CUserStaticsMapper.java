package com.ruoyi.business.mapper;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.model.CUserStatics;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface CUserStaticsMapper extends Mapper<CUserStatics>, MySqlMapper<CUserStatics> {
    CUserStatics getByAddressForUpdate(@Param("address") String address);

    List<JSONObject> getUserDirectList(@Param("address") String address);
}