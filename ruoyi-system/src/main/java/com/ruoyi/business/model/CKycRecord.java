package com.ruoyi.business.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "c_kyc_record")
public class CKycRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @Column(name = "merchantBizId")
    private String merchantbizid;

    @Column(name = "merchantUserId")
    private String merchantuserid;

    @Column(name = "requestId")
    private String requestid;

    @Column(name = "transactionId")
    private String transactionid;

    /**
     * 1 审核中 2 已通过 3 已拒绝
     */
    private Integer status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "kycInfo")
    private String kycinfo;

    /**
     * 真是姓名
     */
    @Column(name = "actual_name")
    private String actualName;

    /**
     * 身份证号
     */
    @Column(name = "identity_number")
    private String identityNumber;

    @Column(name = "identity_type")
    private String identityType;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return merchantBizId
     */
    public String getMerchantbizid() {
        return merchantbizid;
    }

    /**
     * @param merchantbizid
     */
    public void setMerchantbizid(String merchantbizid) {
        this.merchantbizid = merchantbizid;
    }

    /**
     * @return merchantUserId
     */
    public String getMerchantuserid() {
        return merchantuserid;
    }

    /**
     * @param merchantuserid
     */
    public void setMerchantuserid(String merchantuserid) {
        this.merchantuserid = merchantuserid;
    }

    /**
     * @return requestId
     */
    public String getRequestid() {
        return requestid;
    }

    /**
     * @param requestid
     */
    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    /**
     * @return transactionId
     */
    public String getTransactionid() {
        return transactionid;
    }

    /**
     * @param transactionid
     */
    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    /**
     * 获取1 审核中 2 已通过 3 已拒绝
     *
     * @return status - 1 审核中 2 已通过 3 已拒绝
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1 审核中 2 已通过 3 已拒绝
     *
     * @param status 1 审核中 2 已通过 3 已拒绝
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * @return kycInfo
     */
    public String getKycinfo() {
        return kycinfo;
    }

    /**
     * @param kycinfo
     */
    public void setKycinfo(String kycinfo) {
        this.kycinfo = kycinfo;
    }

    public String getActualName() {
        return actualName;
    }

    public void setActualName(String actualName) {
        this.actualName = actualName;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }
}