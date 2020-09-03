package com.ledao.system.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 【请填写功能名称】对象 sys_project_uncollected_money
 *
 * @author ledao
 * @date 2020-08-31
 */
public class SysProjectUncollectedMoney extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 流转表ID
     */
    @Excel(name = "流转表ID")
    private Long projectManagementId;

    /**
     * 资金类型
     */
    @Excel(name = "资金类型")
    private String fundType;

    /**
     * 时间
     */
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    /**
     * 金额
     */
    @Excel(name = "金额")
    private BigDecimal amountMoney;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String state;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectManagementId() {
        return projectManagementId;
    }

    public void setProjectManagementId(Long projectManagementId) {
        this.projectManagementId = projectManagementId;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public BigDecimal getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(BigDecimal amountMoney) {
        this.amountMoney = amountMoney;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("projectManagementId", getProjectManagementId())
                .append("fundType", getFundType())
                .append("time", getTime())
                .append("amountMoney", getAmountMoney())
                .append("state", getState())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
