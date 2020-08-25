package com.ledao.system.domain;

import com.ledao.common.annotation.Excel;
import com.ledao.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 项目管理资产库对象 sys_project_zck
 *
 * @author ledao
 * @date 2020-08-12
 */
public class SysProjectZck extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 项目管理资产库ID
     */
    private Long projectZckId;

    /**
     * 资产库名称
     */
    @Excel(name = "资产库名称")
    private String zckName;

    /**
     * 资产库状态
     */
    @Excel(name = "资产库状态")
    private String zckStatus;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    public Long getProjectZckId() {
        return projectZckId;
    }

    public void setProjectZckId(Long projectZckId) {
        this.projectZckId = projectZckId;
    }

    public void setZckName(String zckName) {
        this.zckName = zckName;
    }

    public String getZckName() {
        return zckName;
    }

    public void setZckStatus(String zckStatus) {
        this.zckStatus = zckStatus;
    }

    public String getZckStatus() {
        return zckStatus;
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
                .append("projectZckId", getProjectZckId())
                .append("zckName", getZckName())
                .append("zckStatus", getZckStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
