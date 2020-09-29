package com.ledao.system.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 目标回收对象 sys_project_targetrecover
 *
 * @author ledao
 * @date 2020-09-03
 */
public class SysProjectTargetrecover extends BaseEntity {
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
     * 目标回收金额
     */
    @Excel(name = "目标回收金额")
    private BigDecimal targetRecoveryAmount;

    /**
     * 目标回收日期
     */
    @Excel(name = "目标回收日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date targetRecoveryDate;

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

    public void setTargetRecoveryAmount(BigDecimal targetRecoveryAmount) {
        this.targetRecoveryAmount = targetRecoveryAmount;
    }

    public BigDecimal getTargetRecoveryAmount() {
        return targetRecoveryAmount;
    }

    public void setTargetRecoveryDate(Date targetRecoveryDate) {
        this.targetRecoveryDate = targetRecoveryDate;
    }

    public Date getTargetRecoveryDate() {
        return targetRecoveryDate;
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
                .append("targetRecoveryAmount", getTargetRecoveryAmount())
                .append("targetRecoveryDate", getTargetRecoveryDate())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
