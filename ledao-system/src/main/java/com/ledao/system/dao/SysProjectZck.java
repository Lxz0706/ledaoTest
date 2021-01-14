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
                .toString();
    }
}
