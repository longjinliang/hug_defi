package com.ruoyi.business.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "c_currency")
public class CCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 币种符号
     */
    private String currency;

    /**
     * 合约地址
     */
    @Column(name = "contract_address")
    private String contractAddress;

    /**
     * 链
     */
    @Column(name = "chain_name")
    private String chainName;

    /**
     * 链id
     */
    @Column(name = "chain_num")
    private Integer chainNum;

    /**
     * 开启充值
     */
    @Column(name = "recharge_enabled")
    private Integer rechargeEnabled;

    /**
     * 开启提现
     */
    @Column(name = "withdraw_enabled")
    private Integer withdrawEnabled;

    /**
     * 提现手续费比例
     */
    @Column(name = "withdraw_fee_ratio")
    private BigDecimal withdrawFeeRatio;

    /**
     * 提现单笔手续费
     */
    @Column(name = "withdraw_fee_amount")
    private BigDecimal withdrawFeeAmount;

    /**
     * 提现最大限额
     */
    @Column(name = "withdraw_max")
    private BigDecimal withdrawMax;

    /**
     * 提现最小额度
     */
    @Column(name = "withdraw_min")
    private BigDecimal withdrawMin;

    /**
     * 当前usdt价格
     */
    @Column(name = "usdt_price")
    private BigDecimal usdtPrice;

    private Integer decimals;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "audit_amount")
    private BigDecimal auditAmount;

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
     * 获取币种符号
     *
     * @return currency - 币种符号
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 设置币种符号
     *
     * @param currency 币种符号
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 获取合约地址
     *
     * @return contract_address - 合约地址
     */
    public String getContractAddress() {
        return contractAddress;
    }

    /**
     * 设置合约地址
     *
     * @param contractAddress 合约地址
     */
    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    /**
     * 获取链
     *
     * @return chain_name - 链
     */
    public String getChainName() {
        return chainName;
    }

    /**
     * 设置链
     *
     * @param chainName 链
     */
    public void setChainName(String chainName) {
        this.chainName = chainName;
    }

    /**
     * 获取链id
     *
     * @return chain_num - 链id
     */
    public Integer getChainNum() {
        return chainNum;
    }

    /**
     * 设置链id
     *
     * @param chainNum 链id
     */
    public void setChainNum(Integer chainNum) {
        this.chainNum = chainNum;
    }

    /**
     * 获取开启充值
     *
     * @return recharge_enabled - 开启充值
     */
    public Integer getRechargeEnabled() {
        return rechargeEnabled;
    }

    /**
     * 设置开启充值
     *
     * @param rechargeEnabled 开启充值
     */
    public void setRechargeEnabled(Integer rechargeEnabled) {
        this.rechargeEnabled = rechargeEnabled;
    }

    /**
     * 获取开启提现
     *
     * @return withdraw_enabled - 开启提现
     */
    public Integer getWithdrawEnabled() {
        return withdrawEnabled;
    }

    /**
     * 设置开启提现
     *
     * @param withdrawEnabled 开启提现
     */
    public void setWithdrawEnabled(Integer withdrawEnabled) {
        this.withdrawEnabled = withdrawEnabled;
    }

    /**
     * 获取提现手续费比例
     *
     * @return withdraw_fee_ratio - 提现手续费比例
     */
    public BigDecimal getWithdrawFeeRatio() {
        return withdrawFeeRatio;
    }

    /**
     * 设置提现手续费比例
     *
     * @param withdrawFeeRatio 提现手续费比例
     */
    public void setWithdrawFeeRatio(BigDecimal withdrawFeeRatio) {
        this.withdrawFeeRatio = withdrawFeeRatio;
    }

    /**
     * 获取提现单笔手续费
     *
     * @return withdraw_fee_amount - 提现单笔手续费
     */
    public BigDecimal getWithdrawFeeAmount() {
        return withdrawFeeAmount;
    }

    /**
     * 设置提现单笔手续费
     *
     * @param withdrawFeeAmount 提现单笔手续费
     */
    public void setWithdrawFeeAmount(BigDecimal withdrawFeeAmount) {
        this.withdrawFeeAmount = withdrawFeeAmount;
    }

    /**
     * 获取提现最大限额
     *
     * @return withdraw_max - 提现最大限额
     */
    public BigDecimal getWithdrawMax() {
        return withdrawMax;
    }

    /**
     * 设置提现最大限额
     *
     * @param withdrawMax 提现最大限额
     */
    public void setWithdrawMax(BigDecimal withdrawMax) {
        this.withdrawMax = withdrawMax;
    }

    /**
     * 获取提现最小额度
     *
     * @return withdraw_min - 提现最小额度
     */
    public BigDecimal getWithdrawMin() {
        return withdrawMin;
    }

    /**
     * 设置提现最小额度
     *
     * @param withdrawMin 提现最小额度
     */
    public void setWithdrawMin(BigDecimal withdrawMin) {
        this.withdrawMin = withdrawMin;
    }

    /**
     * 获取当前usdt价格
     *
     * @return usdt_price - 当前usdt价格
     */
    public BigDecimal getUsdtPrice() {
        return usdtPrice;
    }

    /**
     * 设置当前usdt价格
     *
     * @param usdtPrice 当前usdt价格
     */
    public void setUsdtPrice(BigDecimal usdtPrice) {
        this.usdtPrice = usdtPrice;
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

    public Integer getDecimals() {
        return decimals;
    }

    public void setDecimals(Integer decimals) {
        this.decimals = decimals;
    }

    public BigDecimal getAuditAmount() {
        return auditAmount;
    }

    public void setAuditAmount(BigDecimal auditAmount) {
        this.auditAmount = auditAmount;
    }
}