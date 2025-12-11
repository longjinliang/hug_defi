package com.ruoyi.business.mapper;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.model.CBalance;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.math.BigDecimal;
import java.util.List;

public interface CBalanceMapper extends Mapper<CBalance>, MySqlMapper<CBalance> {
    int addBalance(@Param("id") Long id, @Param("amount") BigDecimal amount,@Param("version")Integer version);

    int frozenBalance(@Param("id") Long id,@Param("amount") BigDecimal amount,@Param("frozenAmount") BigDecimal frozenAmount, @Param("version") Integer version);

    List<JSONObject> getBalanceList(@Param("address") String address,
                                    @Param("coin") String coin,
                                    @Param("balanceType") String balanceType);

    List<CBalance> getBackBalanceList(@Param("address") String address,
                                      @Param("coin") String coin,
                                      @Param("balanceType") String balanceType);

    BigDecimal getSumAmount(@Param("currency") String currency);

    List<JSONObject> getBackBalanceListSum(@Param("address") String address,
                                     @Param("coin") String coin,
                                     @Param("balanceType") String balanceType);

}