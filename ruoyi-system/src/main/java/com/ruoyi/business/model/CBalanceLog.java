package com.ruoyi.business.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "c_balance_log")
public class CBalanceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 余额表id
     */
    @Column(name = "balance_id")
    private Long balanceId;

    /**
     * 用户uid
     */
    private Long uid;

    /**
     * 修改前可用金额
     */
    @Column(name = "before_amount")
    private BigDecimal beforeAmount;

    /**
     * 修改后可用金额
     */
    @Column(name = "after_amount")
    private BigDecimal afterAmount;

    /**
     * 修改前冻结金额
     */
    private BigDecimal amount;

    /**
     * 描述
     */
    private String remarks;

    /**
     * 类型
     */
    private String type;

    /**
     * 引起资金变动的单据id
     */
    private Long fid;

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
     * 获取余额表id
     *
     * @return balance_id - 余额表id
     */
    public Long getBalanceId() {
        return balanceId;
    }

    /**
     * 设置余额表id
     *
     * @param balanceId 余额表id
     */
    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    /**
     * 获取用户uid
     *
     * @return uid - 用户uid
     */
    public Long getUid() {
        return uid;
    }

    /**
     * 设置用户uid
     *
     * @param uid 用户uid
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * 获取修改前可用金额
     *
     * @return before_amount - 修改前可用金额
     */
    public BigDecimal getBeforeAmount() {
        return beforeAmount;
    }

    /**
     * 设置修改前可用金额
     *
     * @param beforeAmount 修改前可用金额
     */
    public void setBeforeAmount(BigDecimal beforeAmount) {
        this.beforeAmount = beforeAmount;
    }

    /**
     * 获取修改后可用金额
     *
     * @return after_amount - 修改后可用金额
     */
    public BigDecimal getAfterAmount() {
        return afterAmount;
    }

    /**
     * 设置修改后可用金额
     *
     * @param afterAmount 修改后可用金额
     */
    public void setAfterAmount(BigDecimal afterAmount) {
        this.afterAmount = afterAmount;
    }

    /**
     * 获取修改前冻结金额
     *
     * @return amount - 修改前冻结金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置修改前冻结金额
     *
     * @param amount 修改前冻结金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取描述
     *
     * @return remarks - 描述
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置描述
     *
     * @param remarks 描述
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
     * 获取引起资金变动的单据id
     *
     * @return fid - 引起资金变动的单据id
     */
    public Long getFid() {
        return fid;
    }

    /**
     * 设置引起资金变动的单据id
     *
     * @param fid 引起资金变动的单据id
     */
    public void setFid(Long fid) {
        this.fid = fid;
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