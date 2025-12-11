package com.ruoyi.business.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "c_arb_block_num")
public class CArbBlockNum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 区块编号
     */
    private Long num;

    /**
     * 区块十六进制编号
     */
    private String hex;

    /**
     * 区块hash
     */
    @Column(name = "block_hash")
    private String blockHash;

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
     * 获取区块编号
     *
     * @return num - 区块编号
     */
    public Long getNum() {
        return num;
    }

    /**
     * 设置区块编号
     *
     * @param num 区块编号
     */
    public void setNum(Long num) {
        this.num = num;
    }

    /**
     * 获取区块十六进制编号
     *
     * @return hex - 区块十六进制编号
     */
    public String getHex() {
        return hex;
    }

    /**
     * 设置区块十六进制编号
     *
     * @param hex 区块十六进制编号
     */
    public void setHex(String hex) {
        this.hex = hex;
    }

    /**
     * 获取区块hash
     *
     * @return block_hash - 区块hash
     */
    public String getBlockHash() {
        return blockHash;
    }

    /**
     * 设置区块hash
     *
     * @param blockHash 区块hash
     */
    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
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