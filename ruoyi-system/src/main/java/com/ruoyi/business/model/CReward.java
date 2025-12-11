package com.ruoyi.business.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "c_reward")
public class CReward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 地址
     */
    private String address;

    /**
     * 币种
     */
    private String coin;

    /**
     * 实际奖励数量
     */
    @Column(name = "reward_amount")
    private BigDecimal rewardAmount;

    /**
     * 价值多少u
     */
    @Column(name = "usdt_amount")
    private BigDecimal usdtAmount;

    /**
     * 质押订单id
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 直推奖励direct,团队奖励team
     */
    @Column(name = "reward_type")
    private String rewardType;

    /**
     * 级别
     */
    @Column(name = "team_level")
    private Integer teamLevel;

    /**
     * 直推奖励direct,团队奖励team
     */
    private String remark;

    /**
     * 是否已提取
     */
    @Column(name = "is_take")
    private Integer isTake;

    /**
     * 提现id
     */
    @Column(name = "withdraw_id")
    private Long withdrawId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "team_count")
    private Integer teamCount;

    @Transient
    private String orderAddress;

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
     * 获取实际奖励数量
     *
     * @return reward_amount - 实际奖励数量
     */
    public BigDecimal getRewardAmount() {
        return rewardAmount;
    }

    /**
     * 设置实际奖励数量
     *
     * @param rewardAmount 实际奖励数量
     */
    public void setRewardAmount(BigDecimal rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    /**
     * 获取质押订单id
     *
     * @return order_id - 质押订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置质押订单id
     *
     * @param orderId 质押订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取直推奖励direct,团队奖励team
     *
     * @return reward_type - 直推奖励direct,团队奖励team
     */
    public String getRewardType() {
        return rewardType;
    }

    /**
     * 设置直推奖励direct,团队奖励team
     *
     * @param rewardType 直推奖励direct,团队奖励team
     */
    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    /**
     * 获取级别
     *
     * @return team_level - 级别
     */
    public Integer getTeamLevel() {
        return teamLevel;
    }

    /**
     * 设置级别
     *
     * @param teamLevel 级别
     */
    public void setTeamLevel(Integer teamLevel) {
        this.teamLevel = teamLevel;
    }

    /**
     * 获取直推奖励direct,团队奖励team
     *
     * @return remark - 直推奖励direct,团队奖励team
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置直推奖励direct,团队奖励team
     *
     * @param remark 直推奖励direct,团队奖励team
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取是否已提取
     *
     * @return is_take - 是否已提取
     */
    public Integer getIsTake() {
        return isTake;
    }

    /**
     * 设置是否已提取
     *
     * @param isTake 是否已提取
     */
    public void setIsTake(Integer isTake) {
        this.isTake = isTake;
    }

    /**
     * 获取提现id
     *
     * @return withdraw_id - 提现id
     */
    public Long getWithdrawId() {
        return withdrawId;
    }

    /**
     * 设置提现id
     *
     * @param withdrawId 提现id
     */
    public void setWithdrawId(Long withdrawId) {
        this.withdrawId = withdrawId;
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

    public Integer getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(Integer teamCount) {
        this.teamCount = teamCount;
    }

    public BigDecimal getUsdtAmount() {
        return usdtAmount;
    }

    public void setUsdtAmount(BigDecimal usdtAmount) {
        this.usdtAmount = usdtAmount;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }
}