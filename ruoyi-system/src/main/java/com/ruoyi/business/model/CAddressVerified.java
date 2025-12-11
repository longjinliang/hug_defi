package com.ruoyi.business.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "c_address_verified")
public class CAddressVerified {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

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
     * 1 审核中 2 已通过 3 已拒绝
     */
    private Integer status;

    private String page1;

    private String page2;

    private String page3;

    /**
     * 申请时间
     */
    @Column(name = "apply_time")
    private Date applyTime;

    /**
     * 审核时间
     */
    @Column(name = "audit_time")
    private Date auditTime;

    /**
     * 审核意见
     */
    private String suggest;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取真是姓名
     *
     * @return actual_name - 真是姓名
     */
    public String getActualName() {
        return actualName;
    }

    /**
     * 设置真是姓名
     *
     * @param actualName 真是姓名
     */
    public void setActualName(String actualName) {
        this.actualName = actualName;
    }

    /**
     * 获取身份证号
     *
     * @return identity_number - 身份证号
     */
    public String getIdentityNumber() {
        return identityNumber;
    }

    /**
     * 设置身份证号
     *
     * @param identityNumber 身份证号
     */
    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
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
     * @return page1
     */
    public String getPage1() {
        return page1;
    }

    /**
     * @param page1
     */
    public void setPage1(String page1) {
        this.page1 = page1;
    }

    /**
     * @return page2
     */
    public String getPage2() {
        return page2;
    }

    /**
     * @param page2
     */
    public void setPage2(String page2) {
        this.page2 = page2;
    }

    /**
     * @return page3
     */
    public String getPage3() {
        return page3;
    }

    /**
     * @param page3
     */
    public void setPage3(String page3) {
        this.page3 = page3;
    }

    /**
     * 获取申请时间
     *
     * @return apply_time - 申请时间
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     * 设置申请时间
     *
     * @param applyTime 申请时间
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * 获取审核时间
     *
     * @return audit_time - 审核时间
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 设置审核时间
     *
     * @param auditTime 审核时间
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 获取审核意见
     *
     * @return suggest - 审核意见
     */
    public String getSuggest() {
        return suggest;
    }

    /**
     * 设置审核意见
     *
     * @param suggest 审核意见
     */
    public void setSuggest(String suggest) {
        this.suggest = suggest;
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

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }
}