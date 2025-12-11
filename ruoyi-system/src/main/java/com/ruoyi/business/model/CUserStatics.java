package com.ruoyi.business.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "c_user_statics")
public class CUserStatics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * uid
     */
    private Long uid;

    private String address;

    /**
     * 直推人数
     */
    @Column(name = "invite_count")
    private Integer inviteCount;

    /**
     * 团队人数
     */
    @Column(name = "team_count")
    private Integer teamCount;

    /**
     * 个人领取次数
     */
    @Column(name = "total_user_count")
    private Integer totalUserCount;

    @Column(name = "user_today_count")
    private Integer userTodayCount;

    /**
     * 用户总领取数量
     */
    @Column(name = "total_user_amount")
    private BigDecimal totalUserAmount;

    /**
     * 团队领取次数
     */
    @Column(name = "total_team_count")
    private Integer totalTeamCount;

    /**
     * 团队领取总数量
     */
    @Column(name = "total_team_amount")
    private BigDecimal totalTeamAmount;

    /**
     * 团队级别
     */
    @Column(name = "team_level")
    private Integer teamLevel;

    /**
     * 团队级别名称
     */
    @Column(name = "team_level_name")
    private String teamLevelName;

    /**
     * 节点级别
     */
    @Column(name = "node_level")
    private Integer nodeLevel;

    /**
     * 用户总领取数量
     */
    @Column(name = "buy_token_amount")
    private BigDecimal buyTokenAmount;

    /**
     * 用户总领取数量
     */
    @Column(name = "buy_usdt_amount")
    private BigDecimal buyUsdtAmount;

    @Column(name = "last_pay_time")
    private Date lastPayTime;

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
     * 获取uid
     *
     * @return uid - uid
     */
    public Long getUid() {
        return uid;
    }

    /**
     * 设置uid
     *
     * @param uid uid
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取直推人数
     *
     * @return invite_count - 直推人数
     */
    public Integer getInviteCount() {
        return inviteCount;
    }

    /**
     * 设置直推人数
     *
     * @param inviteCount 直推人数
     */
    public void setInviteCount(Integer inviteCount) {
        this.inviteCount = inviteCount;
    }

    /**
     * 获取团队人数
     *
     * @return team_count - 团队人数
     */
    public Integer getTeamCount() {
        return teamCount;
    }

    /**
     * 设置团队人数
     *
     * @param teamCount 团队人数
     */
    public void setTeamCount(Integer teamCount) {
        this.teamCount = teamCount;
    }

    /**
     * 获取个人领取次数
     *
     * @return total_user_count - 个人领取次数
     */
    public Integer getTotalUserCount() {
        return totalUserCount;
    }

    /**
     * 设置个人领取次数
     *
     * @param totalUserCount 个人领取次数
     */
    public void setTotalUserCount(Integer totalUserCount) {
        this.totalUserCount = totalUserCount;
    }

    /**
     * 获取用户总领取数量
     *
     * @return total_user_amount - 用户总领取数量
     */
    public BigDecimal getTotalUserAmount() {
        return totalUserAmount;
    }

    /**
     * 设置用户总领取数量
     *
     * @param totalUserAmount 用户总领取数量
     */
    public void setTotalUserAmount(BigDecimal totalUserAmount) {
        this.totalUserAmount = totalUserAmount;
    }

    /**
     * 获取团队领取次数
     *
     * @return total_team_count - 团队领取次数
     */
    public Integer getTotalTeamCount() {
        return totalTeamCount;
    }

    /**
     * 设置团队领取次数
     *
     * @param totalTeamCount 团队领取次数
     */
    public void setTotalTeamCount(Integer totalTeamCount) {
        this.totalTeamCount = totalTeamCount;
    }

    /**
     * 获取团队领取总数量
     *
     * @return total_team_amount - 团队领取总数量
     */
    public BigDecimal getTotalTeamAmount() {
        return totalTeamAmount;
    }

    /**
     * 设置团队领取总数量
     *
     * @param totalTeamAmount 团队领取总数量
     */
    public void setTotalTeamAmount(BigDecimal totalTeamAmount) {
        this.totalTeamAmount = totalTeamAmount;
    }

    /**
     * 获取团队级别
     *
     * @return team_level - 团队级别
     */
    public Integer getTeamLevel() {
        return teamLevel;
    }

    /**
     * 设置团队级别
     *
     * @param teamLevel 团队级别
     */
    public void setTeamLevel(Integer teamLevel) {
        this.teamLevel = teamLevel;
    }

    /**
     * 获取团队级别名称
     *
     * @return team_level_name - 团队级别名称
     */
    public String getTeamLevelName() {
        return teamLevelName;
    }

    /**
     * 设置团队级别名称
     *
     * @param teamLevelName 团队级别名称
     */
    public void setTeamLevelName(String teamLevelName) {
        this.teamLevelName = teamLevelName;
    }

    /**
     * 获取节点级别
     *
     * @return node_level - 节点级别
     */
    public Integer getNodeLevel() {
        return nodeLevel;
    }

    /**
     * 设置节点级别
     *
     * @param nodeLevel 节点级别
     */
    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    /**
     * 获取用户总领取数量
     *
     * @return buy_token_amount - 用户总领取数量
     */
    public BigDecimal getBuyTokenAmount() {
        return buyTokenAmount;
    }

    /**
     * 设置用户总领取数量
     *
     * @param buyTokenAmount 用户总领取数量
     */
    public void setBuyTokenAmount(BigDecimal buyTokenAmount) {
        this.buyTokenAmount = buyTokenAmount;
    }

    /**
     * 获取用户总领取数量
     *
     * @return buy_usdt_amount - 用户总领取数量
     */
    public BigDecimal getBuyUsdtAmount() {
        return buyUsdtAmount;
    }

    /**
     * 设置用户总领取数量
     *
     * @param buyUsdtAmount 用户总领取数量
     */
    public void setBuyUsdtAmount(BigDecimal buyUsdtAmount) {
        this.buyUsdtAmount = buyUsdtAmount;
    }

    /**
     * @return last_pay_time
     */
    public Date getLastPayTime() {
        return lastPayTime;
    }

    /**
     * @param lastPayTime
     */
    public void setLastPayTime(Date lastPayTime) {
        this.lastPayTime = lastPayTime;
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

    public Integer getUserTodayCount() {
        return userTodayCount;
    }

    public void setUserTodayCount(Integer userTodayCount) {
        this.userTodayCount = userTodayCount;
    }
}