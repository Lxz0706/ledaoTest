package com.ledao.system.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 投后部项目管理保证人对象 sys_project_bail
 *
 * @author ledao
 * @date 2020-08-06
 */
public class SysProjectBail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long bailId;

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
     * 保证人
     */
    @Excel(name = "保证人")
    private String bail;

    /**
     * 担保金额(元)
     */
    @Excel(name = "担保金额(元)")
    private BigDecimal amountGuaranteed;

    /**
     * 起始日
     */
    @Excel(name = "起始日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 到期日
     */
    @Excel(name = "到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 已代偿金额(元)
     */
    @Excel(name = "已代偿金额(元)")
    private Double amountCompensated;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    private String projectIds;

    public Long getBailId() {
        return bailId;
    }

    public void setBailId(Long bailId) {
        this.bailId = bailId;
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

    public void setBail(String bail) {
        this.bail = bail;
    }

    public String getBail() {
        return bail;
    }

    public BigDecimal getAmountGuaranteed() {
        return amountGuaranteed;
    }

    public void setAmountGuaranteed(BigDecimal amountGuaranteed) {
        this.amountGuaranteed = amountGuaranteed;
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

    public void setAmountCompensated(Double amountCompensated) {
        this.amountCompensated = amountCompensated;
    }

    public Double getAmountCompensated() {
        return amountCompensated;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("bailId", getBailId())
                .append("projectId", getProjectId())
                .append("projectName", getProjectName())
                .append("contractNo", getContractNo())
                .append("bail", getBail())
                .append("amountGuaranteed", getAmountGuaranteed())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("amountCompensated", getAmountCompensated())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
