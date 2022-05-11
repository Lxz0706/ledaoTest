package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目管理资产库对象 sys_project_zck
 *
 * @author ledao
 * @date 2020-08-12
 */
public class SysProjectZck extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 项目管理资产库ID
     */
    private Long projectZckId;

    /**
     * 资产库名称
     */
    @Excel(name = "资产库名称")
    private String zckName;

    /**
     * 资产库状态
     */
    @Excel(name = "资产库状态")
    private String zckStatus;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 处置开始时间
     */
    private Date startTime;

    /**
     * 处置结束时间
     */
    private Date endTime;

    /**
     * 本金余额
     */
    private BigDecimal bjye;

    private String bjyes;

    /**
     * 处置回现金额
     */
    private BigDecimal czhx;

    private String czhxs;

    /**
     * 剩余债权户数
     */
    private Long syhs;

    /**
     * 总户数
     */
    private Long totalCount;

    /**
     * 剩余百分比
     */
    private Long surplus;

    /**
     * 已完成
     */
    private Long completed;

    /*
     * 总本金余额
     * */
    private BigDecimal totalBjye;

    private BigDecimal totalCzhx;

    /**
     * 法务与投后项目
     */
    //@Excel(name = "法务与投后项目")
    private String fwProjectType;

    /**
     * 资产库类型
     */
    //@Excel(name = "资产库类型")
    private String projectZckType;

    private Long ongoingCount;

    private Long quitCount;

    private Long zckStatusCount;

    /**
     * 分组的id集合
     */
    private String projectZckIds;

    /**
     * 资产库类型对应的名称
     */
    private String dictLable;

    private Long totalZck;

    public Long getProjectZckId() {
        return projectZckId;
    }

    public void setProjectZckId(Long projectZckId) {
        this.projectZckId = projectZckId;
    }

    public void setZckName(String zckName) {
        this.zckName = zckName;
    }

    public String getZckName() {
        return zckName;
    }

    public void setZckStatus(String zckStatus) {
        this.zckStatus = zckStatus;
    }

    public String getZckStatus() {
        return zckStatus;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getBjye() {
        return bjye;
    }

    public void setBjye(BigDecimal bjye) {
        this.bjye = bjye;
    }

    public BigDecimal getCzhx() {
        return czhx;
    }

    public void setCzhx(BigDecimal czhx) {
        this.czhx = czhx;
    }

    public Long getSyhs() {
        return syhs;
    }

    public void setSyhs(Long syhs) {
        this.syhs = syhs;
    }

    public String getBjyes() {
        return bjyes;
    }

    public void setBjyes(String bjyes) {
        this.bjyes = bjyes;
    }

    public String getCzhxs() {
        return czhxs;
    }

    public void setCzhxs(String czhxs) {
        this.czhxs = czhxs;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getSurplus() {
        return surplus;
    }

    public void setSurplus(Long surplus) {
        this.surplus = surplus;
    }

    public Long getCompleted() {
        return completed;
    }

    public void setCompleted(Long completed) {
        this.completed = completed;
    }

    public BigDecimal getTotalBjye() {
        return totalBjye;
    }

    public void setTotalBjye(BigDecimal totalBjye) {
        this.totalBjye = totalBjye;
    }

    public BigDecimal getTotalCzhx() {
        return totalCzhx;
    }

    public void setTotalCzhx(BigDecimal totalCzhx) {
        this.totalCzhx = totalCzhx;
    }

    public String getFwProjectType() {
        return fwProjectType;
    }

    public void setFwProjectType(String fwProjectType) {
        this.fwProjectType = fwProjectType;
    }

    public String getProjectZckType() {
        return projectZckType;
    }

    public void setProjectZckType(String projectZckType) {
        this.projectZckType = projectZckType;
    }

    public Long getOngoingCount() {
        return ongoingCount;
    }

    public void setOngoingCount(Long ongoingCount) {
        this.ongoingCount = ongoingCount;
    }

    public Long getQuitCount() {
        return quitCount;
    }

    public void setQuitCount(Long quitCount) {
        this.quitCount = quitCount;
    }

    public Long getZckStatusCount() {
        return zckStatusCount;
    }

    public void setZckStatusCount(Long zckStatusCount) {
        this.zckStatusCount = zckStatusCount;
    }

    public String getProjectZckIds() {
        return projectZckIds;
    }

    public void setProjectZckIds(String projectZckIds) {
        this.projectZckIds = projectZckIds;
    }

    public String getDictLable() {
        return dictLable;
    }

    public void setDictLable(String dictLable) {
        this.dictLable = dictLable;
    }

    public Long getTotalZck() {
        return totalZck;
    }

    public void setTotalZck(Long totalZck) {
        this.totalZck = totalZck;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("projectZckId", getProjectZckId())
                .append("zckName", getZckName())
                .append("zckStatus", getZckStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("fwProjectType", getFwProjectType())
                .append("projectZckType", getProjectZckType())
                .append("projectZckIds", getProjectZckIds())
                .append("dictLable", getDictLable())
                .append("zckStatusCount", getZckStatusCount())
                .append("syhs", getSyhs())
                .append("totalZck", getTotalZck())
                .toString();
    }
}
