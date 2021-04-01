package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资产包对象 sys_zcb
 *
 * @author lxz
 * @date 2020-06-11
 */
public class SysZcb extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 资产包名称
     */
    @Excel(name = "资产包名称")
    private String assetPackageName;

    /**
     * 资产包状态
     */
    @Excel(name = "资产包状态")
    private String assetStatus;

    /**
     * 小组成员ID
     */
    private String teamMembersId;

    /**
     * 小组成员
     */
    private String teamMembersName;

    private BigDecimal collateralTotal;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    private BigDecimal capValue;

    /**
     * 评估时间
     */
    private Date evaluationTime;

    /**
     * 客户名称
     */
    private String customer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAssetPackageName(String assetPackageName) {
        this.assetPackageName = assetPackageName;
    }

    public String getAssetPackageName() {
        return assetPackageName;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public BigDecimal getCollateralTotal() {
        return collateralTotal;
    }

    public void setCollateralTotal(BigDecimal collateralTotal) {
        this.collateralTotal = collateralTotal;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public BigDecimal getCapValue() {
        return capValue;
    }

    public void setCapValue(BigDecimal capValue) {
        this.capValue = capValue;
    }

    public String getTeamMembersId() {
        return teamMembersId;
    }

    public void setTeamMembersId(String teamMembersId) {
        this.teamMembersId = teamMembersId;
    }

    public String getTeamMembersName() {
        return teamMembersName;
    }

    public void setTeamMembersName(String teamMembersName) {
        this.teamMembersName = teamMembersName;
    }

    public Date getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(Date evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("assetPackageName", getAssetPackageName())
                .append("assetStatus", getAssetStatus())
                .append("delFlag", getDelFlag())
                .append("teamMembersId", getTeamMembersId())
                .append("teamMembersName", getTeamMembersName())
                .append("evaluationTime", getEvaluationTime())
                .toString();
    }
}
