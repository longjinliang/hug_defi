package com.ruoyi.business.mapper;

import com.ruoyi.business.model.CKycRecord;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface CKycRecordMapper extends Mapper<CKycRecord>, MySqlMapper<CKycRecord> {
    CKycRecord getLastKycRecord(@Param("address") String address);
}