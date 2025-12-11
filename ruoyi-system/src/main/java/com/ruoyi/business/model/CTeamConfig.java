package com.ruoyi.business.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "c_team_config")
public class CTeamConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 级别名称
     */
    @Column(name = "level_name")
    private String levelName;

    /**
     * 达到团队业绩
     */
    @Column(name = "team_count")
    private Integer teamCount;

    /**
     * 奖励数量
     */
    @Column(name = "reward_amount")
    private BigDecimal rewardAmount;

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
     * 获取级别
     *
     * @return level - 级别
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置级别
     *
     * @param level 级别
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取级别名称
     *
     * @return level_name - 级别名称
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * 设置级别名称
     *
     * @param levelName 级别名称
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    /**
     * 获取达到团队业绩
     *
     * @return team_amount - 达到团队业绩
     */
    public Integer getTeamCount() {
        return teamCount;
    }

    /**
     * 设置达到团队业绩
     *
     * @param teamCount 达到团队业绩
     */
    public void setTeamCount(Integer teamCount) {
        this.teamCount = teamCount;
    }

    /**
     * 获取奖励数量
     *
     * @return reward_amount - 奖励数量
     */
    public BigDecimal getRewardAmount() {
        return rewardAmount;
    }

    /**
     * 设置奖励数量
     *
     * @param rewardAmount 奖励数量
     */
    public void setRewardAmount(BigDecimal rewardAmount) {
        this.rewardAmount = rewardAmount;
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