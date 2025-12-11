package com.ruoyi.business.mapper;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.model.CUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.Date;
import java.util.List;

public interface CUserMapper extends Mapper<CUser>, MySqlMapper<CUser> {
    List<JSONObject> getBackUserList(@Param("address") String address,
                                     @Param("parentAddress") String parentAddress,
                                     @Param("startTime") Date startTime,
                                     @Param("endTime") Date endTime,
                                     @Param("nodeLevel") Integer nodeLevel);

    int getTeamCount(@Param("userTree") String userTree);

    List<CUser> getNotTokenAmountUserList();

    List<CUser> getNodeUserList();


    void updateUserBakAddress(@Param("address") String address, @Param("toAddress") String toAddress);

    void updateUserBakTeamAddress(@Param("address") String address, @Param("toAddress") String toAddress);

    CUser getBigUser(@Param("address") String address);

    int getCountByIp(@Param("ip") String ip);

    int getChildCountByLevel(@Param("userTree") String userTree, @Param("userDepth") int userDepth,@Param("depth") int depth);

    List<JSONObject> getDirectCountList();

    int getVerifiedCount(@Param("address") String address);
}