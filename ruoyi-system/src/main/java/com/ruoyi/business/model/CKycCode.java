package com.ruoyi.business.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "c_kyc_code")
public class CKycCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 中文名称
     */
    @Column(name = "name_zh")
    private String nameZh;

    /**
     * 英文名称
     */
    @Column(name = "name_en")
    private String nameEn;

    /**
     * 0国内,1国外
     */
    @Column(name = "is_out")
    private Integer isOut;

    /**
     * 编号
     */
    private String code;

    private Integer enabled;

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
     * 获取中文名称
     *
     * @return name_zh - 中文名称
     */
    public String getNameZh() {
        return nameZh;
    }

    /**
     * 设置中文名称
     *
     * @param nameZh 中文名称
     */
    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    /**
     * 获取英文名称
     *
     * @return name_en - 英文名称
     */
    public String getNameEn() {
        return nameEn;
    }

    /**
     * 设置英文名称
     *
     * @param nameEn 英文名称
     */
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    /**
     * 获取0国内,1国外
     *
     * @return is_out - 0国内,1国外
     */
    public Integer getIsOut() {
        return isOut;
    }

    /**
     * 设置0国内,1国外
     *
     * @param isOut 0国内,1国外
     */
    public void setIsOut(Integer isOut) {
        this.isOut = isOut;
    }

    /**
     * 获取编号
     *
     * @return code - 编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编号
     *
     * @param code 编号
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return enabled
     */
    public Integer getEnabled() {
        return enabled;
    }

    /**
     * @param enabled
     */
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
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