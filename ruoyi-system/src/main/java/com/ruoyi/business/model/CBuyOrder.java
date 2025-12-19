package com.ruoyi.business.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "c_buy_order")
public class CBuyOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 地址
     */
    private String address;

    /**
     * 接收地址
     */
    @Column(name = "to_address")
    private String toAddress;

    /**
     * 第几阶段
     */
    @Column(name = "stage_num")
    private Integer stageNum;

    /**
     * 币种
     */
    private String coin;

    /**
     * 价值usdt金额
     */
    @Column(name = "usdt_amount")
    private BigDecimal usdtAmount;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 0未确认，1已成功
     */
    private Integer status;

    /**
     * 获得token数量
     */
    @Column(name = "token_amount")
    private BigDecimal tokenAmount;

    @Column(name = "parent_reward_amount")
    private BigDecimal parentRewardAmount;

    @Column(name = "node_reward_amount")
    private BigDecimal nodeRewardAmount;

    @Column(name = "last_amount")
    private BigDecimal lastAmount;

    private String hash;

    @Column(name = "login_ip")
    private String loginIp;

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
     * 获取接收地址
     *
     * @return to_address - 接收地址
     */
    public String getToAddress() {
        return toAddress;
    }

    /**
     * 设置接收地址
     *
     * @param toAddress 接收地址
     */
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    /**
     * 获取第几阶段
     *
     * @return stage_num - 第几阶段
     */
    public Integer getStageNum() {
        return stageNum;
    }

    /**
     * 设置第几阶段
     *
     * @param stageNum 第几阶段
     */
    public void setStageNum(Integer stageNum) {
        this.stageNum = stageNum;
    }

    /**
     * 获取币种
     *
     * @return coin - 币种
     */
    public String getCoin() {
        return coin;
    }

    /**
     * 设置币种
     *
     * @param coin 币种
     */
    public void setCoin(String coin) {
        this.coin = coin;
    }

    /**
     * 获取价值usdt金额
     *
     * @return usdt_amount - 价值usdt金额
     */
    public BigDecimal getUsdtAmount() {
        return usdtAmount;
    }

    /**
     * 设置价值usdt金额
     *
     * @param usdtAmount 价值usdt金额
     */
    public void setUsdtAmount(BigDecimal usdtAmount) {
        this.usdtAmount = usdtAmount;
    }

    /**
     * 获取价格
     *
     * @return price - 价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price 价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取0未确认，1已成功
     *
     * @return status - 0未确认，1已成功
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0未确认，1已成功
     *
     * @param status 0未确认，1已成功
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取获得token数量
     *
     * @return token_amount - 获得token数量
     */
    public BigDecimal getTokenAmount() {
        return tokenAmount;
    }

    /**
     * 设置获得token数量
     *
     * @param tokenAmount 获得token数量
     */
    public void setTokenAmount(BigDecimal tokenAmount) {
        this.tokenAmount = tokenAmount;
    }

    /**
     * @return hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * @param hash
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * @return login_ip
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * @param loginIp
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
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

    public BigDecimal getParentRewardAmount() {
        return parentRewardAmount;
    }

    public void setParentRewardAmount(BigDecimal parentRewardAmount) {
        this.parentRewardAmount = parentRewardAmount;
    }

    public BigDecimal getNodeRewardAmount() {
        return nodeRewardAmount;
    }

    public void setNodeRewardAmount(BigDecimal nodeRewardAmount) {
        this.nodeRewardAmount = nodeRewardAmount;
    }

    public BigDecimal getLastAmount() {
        return lastAmount;
    }

    public void setLastAmount(BigDecimal lastAmount) {
        this.lastAmount = lastAmount;
    }
}