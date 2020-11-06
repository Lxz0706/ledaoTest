package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 投后项目质押物对象 sys_project_pledge
 *
 * @author lxz
 * @date 2020-10-28
 */
public class SysProjectPledge extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long pledgeId;

    /**
     * 质押物类型
     */
    @Excel(name = "质押物类型")
    private String pledgeType;

    /**
     * 项目ID
     */
    @Excel(name = "项目ID")
    private Long projectId;

    /**
     * 质押合同编号
     */
    @Excel(name = "质押合同编号")
    private String pledgeContractNo;

    /**
     * 质押人
     */
    @Excel(name = "质押人")
    private String pledgor;

    /**
     * 质押金额
     */
    @Excel(name = "质押金额")
    private BigDecimal pledgeAmount;

    /**
     * 质押类型
     */
    @Excel(name = "质押类型")
    private String pledgeCategory;

    /**
     * 最高额确定期间
     */
    @Excel(name = "最高额确定期间")
    private String maximumAmountTime;

    /**
     * 证书编号
     */
    @Excel(name = "证书编号")
    private String certificateNumber;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    private String projectIds;

    private String projectName;

    public Long getPledgeId() {
        return pledgeId;
    }

    public void setPledgeId(Long pledgeId) {
        this.pledgeId = pledgeId;
    }

    public String getPledgeType() {
        return pledgeType;
    }

    public void setPledgeType(String pledgeType) {
        this.pledgeType = pledgeType;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getPledgeContractNo() {
        return pledgeContractNo;
    }

    public void setPledgeContractNo(String pledgeContractNo) {
        this.pledgeContractNo = pledgeContractNo;
    }

    public String getPledgor() {
        return pledgor;
    }

    public void setPledgor(String pledgor) {
        this.pledgor = pledgor;
    }

    public BigDecimal getPledgeAmount() {
        return pledgeAmount;
    }

    public void setPledgeAmount(BigDecimal pledgeAmount) {
        this.pledgeAmount = pledgeAmount;
    }

    public String getPledgeCategory() {
        return pledgeCategory;
    }

    public void setPledgeCategory(String pledgeCategory) {
        this.pledgeCategory = pledgeCategory;
    }

    public String getMaximumAmountTime() {
        return maximumAmountTime;
    }

    public void setMaximumAmountTime(String maximumAmountTime) {
        this.maximumAmountTime = maximumAmountTime;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(String projectIds) {
        this.projectIds = projectIds;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("pledgeId", getPledgeId())
                .append("pledgeType", getPledgeType())
                .append("projectId", getProjectId())
                .append("pledgeContractNo", getPledgeContractNo())
                .append("pledgor", getPledgor())
                .append("pledgeAmount", getPledgeAmount())
                .append("pledgeCategory", getPledgeCategory())
                .append("maximumAmountTime", getMaximumAmountTime())
                .append("certificateNumber", getCertificateNumber())
                .append("remarks", getRemarks())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
