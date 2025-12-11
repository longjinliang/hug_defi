package com.ruoyi.business.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "c_user_google")
public class CUserGoogle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 谷歌码
     */
    @Column(name = "google_code")
    private String googleCode;

    private Integer type;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;


    @Column(name = "is_mapping")
    private Integer isMapping;

    @Column(name = "is_collect")
    private Integer isCollect;

    @Transient
    private String address;

    @Transient
    private String privateKey;

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
     * 获取谷歌码
     *
     * @return google_code - 谷歌码
     */
    public String getGoogleCode() {
        return googleCode;
    }

    /**
     * 设置谷歌码
     *
     * @param googleCode 谷歌码
     */
    public void setGoogleCode(String googleCode) {
        this.googleCode = googleCode;
    }

    /**
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Integer getIsMapping() {
        return isMapping;
    }

    public void setIsMapping(Integer isMapping) {
        this.isMapping = isMapping;
    }

    public Integer getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Integer isCollect) {
        this.isCollect = isCollect;
    }
}