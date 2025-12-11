package com.ruoyi.business.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "c_buy_stage")
public class CBuyStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stage_num")
    private Integer stageNum;

    /**
     * 金额
     */
    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    /**
     * 已购买金额
     */
    @Column(name = "buy_amount")
    private BigDecimal buyAmount;

    /**
     * 价格
     */
    @Column(name = "usdt_price")
    private BigDecimal usdtPrice;

    /**
     * 最低起购量
     */
    @Column(name = "min_usdt_amount")
    private BigDecimal minUsdtAmount;

    /**
     * 0未开始，1进行中，2已售罄，3已结束
     */
    private Integer status;

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
     * @return stage_num
     */
    public Integer getStageNum() {
        return stageNum;
    }

    /**
     * @param stageNum
     */
    public void setStageNum(Integer stageNum) {
        this.stageNum = stageNum;
    }

    /**
     * 获取金额
     *
     * @return total_amount - 金额
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置金额
     *
     * @param totalAmount 金额
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 获取已购买金额
     *
     * @return buy_amount - 已购买金额
     */
    public BigDecimal getBuyAmount() {
        return buyAmount;
    }

    /**
     * 设置已购买金额
     *
     * @param buyAmount 已购买金额
     */
    public void setBuyAmount(BigDecimal buyAmount) {
        this.buyAmount = buyAmount;
    }

    /**
     * 获取价格
     *
     * @return usdt_price - 价格
     */
    public BigDecimal getUsdtPrice() {
        return usdtPrice;
    }

    /**
     * 设置价格
     *
     * @param usdtPrice 价格
     */
    public void setUsdtPrice(BigDecimal usdtPrice) {
        this.usdtPrice = usdtPrice;
    }

    /**
     * 获取最低起购量
     *
     * @return min_usdt_amount - 最低起购量
     */
    public BigDecimal getMinUsdtAmount() {
        return minUsdtAmount;
    }

    /**
     * 设置最低起购量
     *
     * @param minUsdtAmount 最低起购量
     */
    public void setMinUsdtAmount(BigDecimal minUsdtAmount) {
        this.minUsdtAmount = minUsdtAmount;
    }

    /**
     * 获取0未开始，1进行中，2已售罄，3已结束
     *
     * @return status - 0未开始，1进行中，2已售罄，3已结束
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0未开始，1进行中，2已售罄，3已结束
     *
     * @param status 0未开始，1进行中，2已售罄，3已结束
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
}