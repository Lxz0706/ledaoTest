package com.ledao.system.dao;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.dao.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 投后项目现金回现对象 sys_recapture
 *
 * @author lxz
 * @date 2020-11-23
 */
public class SysRecapture extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long recaptureId;

    /**
     * 项目id
     */
    @Excel(name = "项目id")
    private Long projectId;

    /**
     * 现金回现
     */
    @Excel(name = "现金回现")
    private BigDecimal recapture;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

    /**
     * 删除标志
     */
    private String delFlag;

    /**
     * 回现时间
     */
    private Date recaptureTime;

    private BigDecimal totalRecapture;

    private String zckIds;

    public Long getRecaptureId() {
        return recaptureId;
    }

    public void setRecaptureId(Long recaptureId) {
        this.recaptureId = recaptureId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getRecapture() {
        return recapture;
    }

    public void setRecapture(BigDecimal recapture) {
        this.recapture = recapture;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public Date getRecaptureTime() {
        return recaptureTime;
    }

    public void setRecaptureTime(Date recaptureTime) {
        this.recaptureTime = recaptureTime;
    }

    public BigDecimal getTotalRecapture() {
        return totalRecapture;
    }

    public void setTotalRecapture(BigDecimal totalRecapture) {
        this.totalRecapture = totalRecapture;
    }


    public String getZckIds() {
        return zckIds;
    }

    public void setZckIds(String zckIds) {
        this.zckIds = zckIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("recaptureId", getRecaptureId())
                .append("projectId", getProjectId())
                .append("recapture", getRecapture())
                .append("remarks", getRemarks())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("recaptureTime", getRecaptureTime())
                .append("totalRecapture", getTotalRecapture())
                .append("zckIds", getZckIds())
                .toString();
    }
}
