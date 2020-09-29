package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 投后部项目管理抵押物对象 sys_project_mortgage
 *
 * @author ledao
 * @date 2020-08-06
 */
public class SysProjectMortgage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 抵押物ID
     */
    private Long mortgageId;

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
     * 抵押物类型
     */
    @Excel(name = "抵押物类型")
    private String collateralType;

    /**
     * 抵押合同编号
     */
    @Excel(name = "抵押合同编号")
    private String mortgageContractNo;

    /**
     * 抵押人
     */
    @Excel(name = "抵押人")
    private String mortgagor;

    /**
     * 抵押金额
     */
    @Excel(name = "抵押金额")
    private Double mortgageAmount;

    /**
     * 抵押类型
     */
    @Excel(name = "抵押类型")
    private String mortgageStatus;

    /**
     * 最高额确定期间
     */
    @Excel(name = "最高额确定期间")
    private String maximumAmountTime;

    /**
     * 他项权证编号
     */
    @Excel(name = "他项权证编号")
    private String otherWarrantsNumber;

    /**
     * 土地面积(㎡)
     */
    @Excel(name = "土地面积(㎡)")
    private String landArea;

    /**
     * 建筑物面积(㎡)
     */
    @Excel(name = "建筑物面积(㎡)")
    private String buildingArea;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    private String projectIds;

    public Long getMortgageId() {
        return mortgageId;
    }

    public void setMortgageId(Long mortgageId) {
        this.mortgageId = mortgageId;
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

    public void setCollateralType(String collateralType) {
        this.collateralType = collateralType;
    }

    public String getCollateralType() {
        return collateralType;
    }

    public void setMortgageContractNo(String mortgageContractNo) {
        this.mortgageContractNo = mortgageContractNo;
    }

    public String getMortgageContractNo() {
        return mortgageContractNo;
    }

    public void setMortgagor(String mortgagor) {
        this.mortgagor = mortgagor;
    }

    public String getMortgagor() {
        return mortgagor;
    }

    public void setMortgageAmount(Double mortgageAmount) {
        this.mortgageAmount = mortgageAmount;
    }

    public Double getMortgageAmount() {
        return mortgageAmount;
    }

    public void setMortgageStatus(String mortgageStatus) {
        this.mortgageStatus = mortgageStatus;
    }

    public String getMortgageStatus() {
        return mortgageStatus;
    }

    public void setMaximumAmountTime(String maximumAmountTime) {
        this.maximumAmountTime = maximumAmountTime;
    }

    public String getMaximumAmountTime() {
        return maximumAmountTime;
    }

    public void setOtherWarrantsNumber(String otherWarrantsNumber) {
        this.otherWarrantsNumber = otherWarrantsNumber;
    }

    public String getOtherWarrantsNumber() {
        return otherWarrantsNumber;
    }

    public void setLandArea(String landArea) {
        this.landArea = landArea;
    }

    public String getLandArea() {
        return landArea;
    }

    public void setBuildingArea(String buildingArea) {
        this.buildingArea = buildingArea;
    }

    public String getBuildingArea() {
        return buildingArea;
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
                .append("mortgageId", getMortgageId())
                .append("projectId", getProjectId())
                .append("projectName", getProjectName())
                .append("collateralType", getCollateralType())
                .append("mortgageContractNo", getMortgageContractNo())
                .append("mortgagor", getMortgagor())
                .append("mortgageAmount", getMortgageAmount())
                .append("mortgageStatus", getMortgageStatus())
                .append("maximumAmountTime", getMaximumAmountTime())
                .append("otherWarrantsNumber", getOtherWarrantsNumber())
                .append("landArea", getLandArea())
                .append("buildingArea", getBuildingArea())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
