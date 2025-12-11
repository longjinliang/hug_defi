package com.ruoyi.business.model;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.sign.Md5Utils;

import java.util.Date;
import javax.persistence.*;

@Table(name = "c_user_tmp")
public class CUserTmp {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_id")
    private Long businessId;

    @Column(name = "business_type")
    private String businessType;

    private String info;

    private Integer status;

    private Long oid;

    private String fingerprint;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private String remark;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return business_id
     */
    public Long getBusinessId() {
        return businessId;
    }

    /**
     * @param businessId
     */
    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    /**
     * @return business_type
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * @param businessType
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    /**
     * @return info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return oid
     */
    public Long getOid() {
        return oid;
    }

    /**
     * @param oid
     */
    public void setOid(Long oid) {
        this.oid = oid;
    }

    /**
     * @return fingerprint
     */
    public String getFingerprint() {
        return fingerprint;
    }

    /**
     * @param fingerprint
     */
    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void initFingerprint() {
        this.fingerprint = Md5Utils.hash(this.getFingerprintBase());
    }

    public boolean getCheckFingerprint() {
        return this.fingerprint!=null && this.fingerprint.equals(Md5Utils.hash(this.getFingerprintBase()));
    }

    private String getFingerprintBase() {
        return new StringBuffer()
                .append("businessId=" + getBusinessId())
                .append("businessType=" + getBusinessType())
                .append("info=" + getInfo())
                .append("status=" + getStatus())
                .append("Oid=" + getOid())
                .toString();
    }


}