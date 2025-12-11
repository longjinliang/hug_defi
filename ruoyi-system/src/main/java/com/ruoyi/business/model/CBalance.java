package com.ruoyi.business.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "c_balance")
public class CBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * uid
     */
    private Long uid;

    private String address;

    /**
     * 通证币
     */
    private String currency;

    /**
     * 币种id
     */
    @Column(name = "currency_id")
    private Long currencyId;

    /**
     * 账户类型
     */
    @Column(name = "balance_type")
    private String balanceType;

    /**
     * 可用金额
     */
    private BigDecimal amount;

    @Column(name = "frozen_amount")
    private BigDecimal frozenAmount;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private Integer version;

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

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getUid() {
        return uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取通证币
     *
     * @return currency - 通证币
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 设置通证币
     *
     * @param currency 通证币
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
     * 获取可用金额
     *
     * @return amount - 可用金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置可用金额
     *
     * @param amount 可用金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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


    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }
}