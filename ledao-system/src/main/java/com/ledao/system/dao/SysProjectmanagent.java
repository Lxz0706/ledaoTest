package com.ledao.system.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 sys_projectmanagent
 *
 * @author ledao
 * @date 2020-08-26
 */
public class SysProjectmanagent extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long projectManagementId;

    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    private String projectManagementName;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private String no;

    /**
     * 项目负责人
     */
    @Excel(name = "项目负责人")
    private String projectLeader;

    /**
     * 优先方
     */
    @Excel(name = "优先方")
    private String priority;

    /**
     * 劣后方
     */
    @Excel(name = "劣后方")
    private String inferiorRear;

    /**
     * 投资日期
     */
    @Excel(name = "投资日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date investmentDate;

    /**
     * 投资金额
     */
    @Excel(name = "投资金额")
    private BigDecimal investmentAmount;

    /**
     * 收支结算方式
     */
    @Excel(name = "收支结算方式")
    private String expensesReceipts;

    /**
     * 已付日期
     */
    @Excel(name = "已付日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date paidDate;

    /**
     * 已付金额
     */
    @Excel(name = "已付金额")
    private BigDecimal amountPaid;

    /**
     * 应（未）付日期
     */
    @Excel(name = "应", readConverterExp = "未=")
    private Date dueDate;

    /**
     * 应（未）付金额
     */
    @Excel(name = "应", readConverterExp = "未=")
    private BigDecimal amountDue;

    /**
     * 已收日期
     */
    @Excel(name = "已收日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receivedDate;

    /**
     * 已收金额
     */
    @Excel(name = "已收金额")
    private BigDecimal amountReceived;

    /**
     * 应（未）收日期
     */
    @Excel(name = "应", readConverterExp = "未=")
    private Date receivableDate;

    /**
     * 应（未）收金额
     */
    @Excel(name = "应", readConverterExp = "未=")
    private BigDecimal amountReceivable;

    /**
     * 投资机构
     */
    @Excel(name = "投资机构")
    private String investmentInstituions;

    /**
     * 清收任务回收金额
     */
    @Excel(name = "清收任务回收金额")
    private BigDecimal amountRecovered;

    /**
     * 清收任务日期
     */
    @Excel(name = "清收任务日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recoveryDate;

    /**
     * 服务费结算方式
     */
    @Excel(name = "服务费结算方式")
    private String serviceFee;

    /**
     * 已收服务费入账金额
     * 已回收金额
     */
    @Excel(name = "已收服务费入账金额")
    private BigDecimal entryAmount;

    /**
     * 已收服务费入账日期
     */
    @Excel(name = "已收服务费入账日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date entryDate;

    /**
     * 未收服务费预期金额
     */
    @Excel(name = "未收服务费预期金额")
    private BigDecimal expectedAmount;

    /**
     * 未收服务费预期日期
     */
    @Excel(name = "未收服务费预期日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expectedDate;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 进度情况
     */
    private String progress;

    /**
     * 目标回收金额
     */
    private BigDecimal targetRecoveryAmount;

    /**
     * 目标回收日期
     * */
    private Date targetRecoverDate;


    public Long getProjectManagementId() {
        return projectManagementId;
    }

    public void setProjectManagementId(Long projectManagementId) {
        this.projectManagementId = projectManagementId;
    }

    public String getProjectManagementName() {
        return projectManagementName;
    }

    public void setProjectManagementName(String projectManagementName) {
        this.projectManagementName = projectManagementName;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getInferiorRear() {
        return inferiorRear;
    }

    public void setInferiorRear(String inferiorRear) {
        this.inferiorRear = inferiorRear;
    }

    public Date getInvestmentDate() {
        return investmentDate;
    }

    public void setInvestmentDate(Date investmentDate) {
        this.investmentDate = investmentDate;
    }

    public BigDecimal getInvestmentAmount() {
        return investmentAmount;
    }

    public void setInvestmentAmount(BigDecimal investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public String getExpensesReceipts() {
        return expensesReceipts;
    }

    public void setExpensesReceipts(String expensesReceipts) {
        this.expensesReceipts = expensesReceipts;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(BigDecimal amountDue) {
        this.amountDue = amountDue;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public BigDecimal getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(BigDecimal amountReceived) {
        this.amountReceived = amountReceived;
    }

    public Date getReceivableDate() {
        return receivableDate;
    }

    public void setReceivableDate(Date receivableDate) {
        this.receivableDate = receivableDate;
    }

    public BigDecimal getAmountReceivable() {
        return amountReceivable;
    }

    public void setAmountReceivable(BigDecimal amountReceivable) {
        this.amountReceivable = amountReceivable;
    }

    public String getInvestmentInstituions() {
        return investmentInstituions;
    }

    public void setInvestmentInstituions(String investmentInstituions) {
        this.investmentInstituions = investmentInstituions;
    }

    public BigDecimal getAmountRecovered() {
        return amountRecovered;
    }

    public void setAmountRecovered(BigDecimal amountRecovered) {
        this.amountRecovered = amountRecovered;
    }

    public Date getRecoveryDate() {
        return recoveryDate;
    }

    public void setRecoveryDate(Date recoveryDate) {
        this.recoveryDate = recoveryDate;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getEntryAmount() {
        return entryAmount;
    }

    public void setEntryAmount(BigDecimal entryAmount) {
        this.entryAmount = entryAmount;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public BigDecimal getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(BigDecimal expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public BigDecimal getTargetRecoveryAmount() {
        return targetRecoveryAmount;
    }

    public void setTargetRecoveryAmount(BigDecimal targetRecoveryAmount) {
        this.targetRecoveryAmount = targetRecoveryAmount;
    }

    public Date getTargetRecoverDate() {
        return targetRecoverDate;
    }

    public void setTargetRecoverDate(Date targetRecoverDate) {
        this.targetRecoverDate = targetRecoverDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("projectManagementId", getProjectManagementId())
                .append("projectManagementName", getProjectManagementName())
                .append("no", getNo())
                .append("projectLeader", getProjectLeader())
                .append("priority", getPriority())
                .append("inferiorRear", getInferiorRear())
                .append("investmentDate", getInvestmentDate())
                .append("investmentAmount", getInvestmentAmount())
                .append("expensesReceipts", getExpensesReceipts())
                .append("paidDate", getPaidDate())
                .append("amountPaid", getAmountPaid())
                .append("dueDate", getDueDate())
                .append("amountDue", getAmountDue())
                .append("receivedDate", getReceivedDate())
                .append("amountReceived", getAmountReceived())
                .append("receivableDate", getReceivableDate())
                .append("amountReceivable", getAmountReceivable())
                .append("investmentInstituions", getInvestmentInstituions())
                .append("amountRecovered", getAmountRecovered())
                .append("recoveryDate", getRecoveryDate())
                .append("serviceFee", getServiceFee())
                .append("entryAmount", getEntryAmount())
                .append("entryDate", getEntryDate())
                .append("expectedAmount", getExpectedAmount())
                .append("expectedDate", getExpectedDate())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
