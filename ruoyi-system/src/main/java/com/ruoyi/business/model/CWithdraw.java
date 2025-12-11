package com.ruoyi.business.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "c_withdraw")
public class CWithdraw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 地址
     */
    private String address;

    /**
     * 用户id
     */
    private Long uid;

    /**
     * 币种
     */
    private String currency;

    /**
     * 币种id
     */
    @Column(name = "currency_id")
    private Long currencyId;

    /**
     * 金额
     */
    private BigDecimal amount;

    private BigDecimal fee;

    @Column(name = "last_amount")
    private BigDecimal lastAmount;

    /**
     * hash
     */
    private String hash;

    private Integer status;

    @Column(name = "audit_time")
    private Date auditTime;

    /**
     * 类型
     */
    private String type;

    /**
     * 奖励id
     */
    @Column(name = "reward_id")
    private Long rewardId;

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
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取用户id
     *
     * @return uid - 用户id
     */
    public Long getUid() {
        return uid;
    }

    /**
     * 设置用户id
     *
     * @param uid 用户id
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * 获取币种
     *
     * @return currency - 币种
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 设置币种
     *
     * @param currency 币种
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 获取币种id
     *
     * @return currency_id - 币种id
     */
    public Long getCurrencyId() {
        return currencyId;
    }

    /**
     * 设置币种id
     *
     * @param currencyId 币种id
     */
    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * 获取金额
     *
     * @return amount - 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置金额
     *
     * @param amount 金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return fee
     */
    public BigDecimal getFee() {
        return fee;
    }

    /**
     * @param fee
     */
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    /**
     * @return last_amount
     */
    public BigDecimal getLastAmount() {
        return lastAmount;
    }

    /**
     * @param lastAmount
     */
    public void setLastAmount(BigDecimal lastAmount) {
        this.lastAmount = lastAmount;
    }

    /**
     * 获取hash
     *
     * @return hash - hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * 设置hash
     *
     * @param hash hash
     */
    public void setHash(String hash) {
        this.hash = hash;
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
     * @return audit_time
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * @param auditTime
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取奖励id
     *
     * @return reward_id - 奖励id
     */
    public Long getRewardId() {
        return rewardId;
    }

    /**
     * 设置奖励id
     *
     * @param rewardId 奖励id
     */
    public void setRewardId(Long rewardId) {
        this.rewardId = rewardId;
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
}