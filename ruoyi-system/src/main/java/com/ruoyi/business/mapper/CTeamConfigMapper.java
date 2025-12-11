package com.ruoyi.business.mapper;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.model.CTeamConfig;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

public interface CTeamConfigMapper extends Mapper<CTeamConfig>, MySqlMapper<CTeamConfig> {
    List<JSONObject> getList(@Param("address") String address);
}