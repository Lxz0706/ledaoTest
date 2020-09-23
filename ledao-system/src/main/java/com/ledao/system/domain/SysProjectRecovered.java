package com.ledao.system.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 已回收对象 sys_project_recovered
 *
 * @author ledao
 * @date 2020-09-03
 */
public class SysProjectRecovered extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 主表id
     */
    @Excel(name = "主表id")
    private Long projectManagementId;

    /**
     * 已回收金额
     */
    @Excel(name = "已回收金额")
    private BigDecimal amountRecovered;

    /**
     * 已回收日期
     */
    @Excel(name = "已回收日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recoveredDate;

    /**
     * 删除标志
     */
    private String delFlag;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setProjectManagementId(Long projectManagementId) {
        this.projectManagementId = projectManagementId;
    }

    public Long getProjectManagementId() {
        return projectManagementId;
    }

    public void setAmountRecovered(BigDecimal amountRecovered) {
        this.amountRecovered = amountRecovered;
    }

    public BigDecimal getAmountRecovered() {
        return amountRecovered;
    }

    public void setRecoveredDate(Date recoveredDate) {
        this.recoveredDate = recoveredDate;
    }

    public Date getRecoveredDate() {
        return recoveredDate;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("projectManagementId", getProjectManagementId())
                .append("amountRecovered", getAmountRecovered())
                .append("recoveredDate", getRecoveredDate())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}