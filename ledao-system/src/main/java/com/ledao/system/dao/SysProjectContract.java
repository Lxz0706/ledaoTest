package com.ledao.system.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 投后部项目管理合同本金对象 sys_project_contract
 *
 * @author ledao
 * @date 2020-08-06
 */
public class SysProjectContract extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long contractId;

    /**
     * 项目ID
     */
    @Excel(name = "项目ID")
    private Long projectId;

    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    private String projectName;

    /**
     * 合同编号
     */
    @Excel(name = "合同编号")
    private String contractNo;

    /**
     * 本金
     */
    @Excel(name = "本金")
    private BigDecimal capital;

    /**
     * 期内起始日
     */
    @Excel(name = "期内起始日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 期内到期日
     */
    @Excel(name = "期内到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 利率
     */
    @Excel(name = "利率")
    private BigDecimal interestRate;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    private String projectIds;

    /**
     * 是否逾期
     */
    private String overdue;

    /**
     * 利息
     */
    private BigDecimal interest;

    /**
     * 逾期利息
     */
    private BigDecimal overdueInterest;

    /**
     * 总利息
     */
    private BigDecimal totalInterest;

    /**
     * 利息复利
     */
    private BigDecimal compoundInterest;

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractNo() {
        return contractNo;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public String getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(String projectIds) {
        this.projectIds = projectIds;
    }

    public String getOverdue() {
        return overdue;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getOverdueInterest() {
        return overdueInterest;
    }

    public void setOverdueInterest(BigDecimal overdueInterest) {
        this.overdueInterest = overdueInterest;
    }

    public BigDecimal getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(BigDecimal totalInterest) {
        this.totalInterest = totalInterest;
    }

    public BigDecimal getCompoundInterest() {
        return compoundInterest;
    }

    public void setCompoundInterest(BigDecimal compoundInterest) {
        this.compoundInterest = compoundInterest;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("contractId", getContractId())
                .append("projectId", getProjectId())
                .append("projectName", getProjectName())
                .append("contractNo", getContractNo())
                .append("capital", getCapital())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("interestRate", getInterestRate())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("overdue", getOverdue())
                .append("interest", getInterest())
                .append("overdueInterest", getOverdueInterest())
                .append("totalInterest", getTotalInterest())
                .append("compoundInterest", getCompoundInterest())
                .toString();
    }
}
