package com.ruoyi.business.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "c_pay_count_record")
public class CPayCountRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 地址
     */
    private String address;

    /**
     * 质押订单id
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 地址
     */
    @Column(name = "order_address")
    private String orderAddress;

    /**
     * 层级别
     */
    @Column(name = "child_level")
    private Integer childLevel;

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
     * 获取地址
     *
     * @return order_address - 地址
     */
    public String getOrderAddress() {
        return orderAddress;
    }

    /**
     * 设置地址
     *
     * @param orderAddress 地址
     */
    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    /**
     * 获取层级别
     *
     * @return child_level - 层级别
     */
    public Integer getChildLevel() {
        return childLevel;
    }

    /**
     * 设置层级别
     *
     * @param childLevel 层级别
     */
    public void setChildLevel(Integer childLevel) {
        this.childLevel = childLevel;
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