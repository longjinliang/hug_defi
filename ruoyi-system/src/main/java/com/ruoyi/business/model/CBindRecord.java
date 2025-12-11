package com.ruoyi.business.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "c_bind_record")
public class CBindRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 地址
     */
    private String address;

    /**
     * 推荐人地址
     */
    @Column(name = "parent_address")
    private String parentAddress;

    /**
     * hash
     */
    private String hash;

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
     * 获取推荐人地址
     *
     * @return parent_address - 推荐人地址
     */
    public String getParentAddress() {
        return parentAddress;
    }

    /**
     * 设置推荐人地址
     *
     * @param parentAddress 推荐人地址
     */
    public void setParentAddress(String parentAddress) {
        this.parentAddress = parentAddress;
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